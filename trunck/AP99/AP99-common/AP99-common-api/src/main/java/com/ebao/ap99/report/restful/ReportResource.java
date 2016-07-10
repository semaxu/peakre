/*package com.ebao.ap99.report.restful;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.restlet.representation.Representation;

import com.ebao.unicorn.platform.foundation.restful.annotation.Module;

@Module(Module.PUBLIC)
@Path("/report")
public class ReportResource extends HttpServlet {
        private static final long serialVersionUID = 1L;

        private String runTime;
       
        private String pattern;
       
   
        @POST
        @Path("/printing/{businessId}")
        @Consumes("multipart/form-data")
        @Produces(MediaType.APPLICATION_JSON)
        public void init(Representation entity, @PathParam("businessId") String businessId) {
               
                //        runTime="2009-05-03 21:42";
                        pattern ="yyyy-MM-dd hh:mm";
                        SimpleDateFormat format = new SimpleDateFormat(pattern);
                        Calendar cal = Calendar.getInstance();
                        //    cal.setTime(format.parse(runTime));
                            Timer t = new Timer();
                            t.schedule(new TimerTask(){  //这里我new了一个匿名类，做为schedule的第一个参数
                                    @Override
                                    public void run() {
                                            runReport();       //当前时间是我们指定的时间时执行运行报表这个方法
                                    }
                                   
                            }, cal.getTime()); //cal.getTime()这是schedule的第二个参数
        }
       

        private void  runReport() {
                 IReportEngine birtEngine = null;
                  EngineConfig config = null;
               
                config = new  EngineConfig();
                config.setEngineHome("");
              //  IPlatformContext context = new PlatformServletContext(sc);
              //  config.setPlatformContext(context);

                try {
                        Platform.startup(config);
                } catch (BirtException e) {
                        e.printStackTrace();
                }
                IReportEngineFactory factory = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
                birtEngine = factory.createReportEngine(config);
                IReportRunnable design;
                try
                {
                        design = birtEngine.openReportDesign("D:\\fileupload\\my_report.rptdesign" );
                        IRunAndRenderTask task = birtEngine.createRunAndRenderTask( design );               
                        HTMLRenderOption options = new HTMLRenderOption();               
                        options.setOutputFileName("D:\\fileupload\\test2.pdf" ); //生成pdf文件保存在output文件夹里。
                        options.setOutputFormat("pdf");
                        options.setHtmlRtLFlag(true);
                        options.setEmbeddable(true);
                        task.setRenderOption(options);
                       
                        task.run();
                        task.close();
                        System.out.println("success!");
                       
                        // use the filename of your report
                       
                }catch (Exception e){
                        e.printStackTrace();
                }
        }


}*/