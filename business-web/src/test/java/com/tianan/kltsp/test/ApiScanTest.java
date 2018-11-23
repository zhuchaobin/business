package com.tianan.kltsp.test;
import java.io.IOException;

/***
 * 扫描获取实现某个注解的类
 * @author ssr
 */
public class ApiScanTest {
	
//    @Test
    public void queryCarData() {


        //otsService.getRange(start,start,)

    }
    
	public static void main(String[] args) throws IOException {
	
//		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//		Resource[] resources = resourcePatternResolver.getResources("classpath*:com/tianan/**/*.class");
		
		/*		SimpleMetadataReaderFactory ft = new SimpleMetadataReaderFactory();
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
		}*/
	}		
}
