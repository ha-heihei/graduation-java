package com.lh;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.lh.entity.BaseEntity;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author LiHao
 * @create 20220115 16:42
 */
public class CodeGenerator {
    @Test
    public void run() {
        String url = "jdbc:mysql://localhost:3306/graduation_project?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true";
        FastAutoGenerator.create(url,"root","root")
                .globalConfig(builder -> {
                    builder.author("lihao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()
                            .outputDir("E:\\IDEA\\workspace\\graduation\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.lh") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "E:\\IDEA\\workspace\\graduation\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("public_material")
//                            .addInclude("group","user","group_user","group_material","material","material_user")
                    .entityBuilder()
                    .enableLombok()
                    .enableTableFieldAnnotation()
                    .superClass(BaseEntity.class)
                    .build()
                    .mapperBuilder()
                    .enableMapperAnnotation()
                    .enableBaseResultMap()
                    .enableBaseColumnList()
                    .build()
                    .controllerBuilder()
                    .enableRestStyle()
                    .build();

                })
                .execute();

    }
}

