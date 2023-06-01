package mx.uv.practica03;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class Configuracion extends WsConfigurerAdapter {
    @Bean
    public XsdSchema saludosSchema() {
        return new SimpleXsdSchema(new ClassPathResource("esquema.xsd"));
    }
    
    @Bean
    //public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
    public List<ServletRegistrationBean<MessageDispatcherServlet>> messageDispatcherServlets(ApplicationContext applicationContext) {

	MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	servlet.setApplicationContext(applicationContext);        
        servlet.setTransformWsdlLocations(true);

	List<ServletRegistrationBean<MessageDispatcherServlet>> servletRegistrationBeans = new ArrayList<>();
	ServletRegistrationBean<MessageDispatcherServlet> registrationBean1 = new ServletRegistrationBean<>(servlet, "/ws/*");
    	ServletRegistrationBean<MessageDispatcherServlet> registrationBean2 = new ServletRegistrationBean<>(servlet, "/api/*");

   	servletRegistrationBeans.add(registrationBean1);
    	servletRegistrationBeans.add(registrationBean2);

	return servletRegistrationBeans;
        //return new ServletRegistrationBean<>(servlet, "/s/*");
    }

    @Bean(name = "saludos")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema saludosSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("saludosPort");        
        wsdl.setLocationUri("/ws");   
        wsdl.setTargetNamespace("https://t4is.uv.mx/saludos");        
        wsdl.setSchema(saludosSchema);
        return wsdl;
    }
}
