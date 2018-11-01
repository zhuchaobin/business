package com.tianan.kltsp.test;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/***
 * 扫描获取实现某个注解的类
 * @author ssr
 */
public class ApiScanTest {
	public static void main(String[] args) throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatternResolver.getResources("classpath*:com/tianan/**/*.class");
		
		SimpleMetadataReaderFactory ft = new SimpleMetadataReaderFactory();
		for (Resource resource : resources) {
			MetadataReader metadataReader = ft.getMetadataReader(resource);
			AnnotationMetadata adm = metadataReader.getAnnotationMetadata();
			if(adm.hasAnnotation("org.springframework.stereotype.Controller")) {
				
				String[] arr =metadataReader.getClassMetadata().getClassName().split("\\.");
				String className = arr[arr.length-1];
				
				AnnotationMetadataReadingVisitor cmr = (AnnotationMetadataReadingVisitor)metadataReader.getClassMetadata();
				for (MethodMetadata mm : cmr.getAnnotatedMethods("org.springframework.web.bind.annotation.RequestMapping")) {
					System.out.println("insert into api_info(api_code) values('" + className + "-" + mm.getMethodName() + "');");
				}
				
//				ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
//				sbd.setResource(resource);
//				sbd.setSource(resource);
//				
//				System.out.println(sbd.getBeanClass().getName());
			}
		}
	}		
}
