package it.admiral0.technicantani

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import javax.servlet.GenericServlet
import javax.servlet.Servlet
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import kotlin.platform.platformStatic


EnableAutoConfiguration ComponentScan Configuration
open class TechnicAntani : SpringBootServletInitializer()  {
    Bean public open fun dispatcherServlet() : Servlet {
        return object : GenericServlet() {
            override fun service(req: ServletRequest?, res: ServletResponse?) {
                res?.setContentType("text/plain")
                res?.getWriter()?.append("Hello World")
            }
        }
    }

    companion object {
        platformStatic public fun main(args : Array<String>){
            SpringApplication.run(javaClass<TechnicAntani>(), *args)
        }
    }
}

