package com.app.nfprocessor.config;


import com.app.nfprocessor.batch.NotaFiscalItemWriter;
import com.app.nfprocessor.batch.NotaFiscalProcessor;
import com.app.nfprocessor.model.NotaFiscal;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public FlatFileItemReader<NotaFiscal> reader() {
        FlatFileItemReader<NotaFiscal> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("input/notas_fiscais.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<NotaFiscal> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "cnpj", "valor");

        BeanWrapperFieldSetMapper<NotaFiscal> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(NotaFiscal.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, NotaFiscalProcessor processor, NotaFiscalItemWriter writer) {
        return new StepBuilder("step", jobRepository)
                .<NotaFiscal, NotaFiscal>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
}