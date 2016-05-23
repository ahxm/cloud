package registry.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by anzhen on 2016/5/23.
 */
public class XmlUtil {

    /**
     * 反序列化
     *
     * @param context
     * @param clazz
     * @param source
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    public static <T> T unmarshal(JAXBContext context, Class<T> clazz, String source) throws JAXBException {

        if (source == null) {
            return null;
        }

        StringReader reader = new StringReader(source);
        if (context == null) {
            context = JAXBContext.newInstance(clazz);
        }
        Unmarshaller un = context.createUnmarshaller();
        return (T) un.unmarshal(reader);
    }

    public static <T> T unmarshal(Class<T> clazz, String source) throws JAXBException {
        return unmarshal(null, clazz, source);
    }

    /**
     * 序列化
     *
     * @param context
     * @param object
     * @param writer
     * @param properties
     * @throws javax.xml.bind.JAXBException
     */

    public static void marshal(JAXBContext context, Object object, Writer writer, Map<String, Object> properties) throws JAXBException {
        if (object == null) {
            return;
        }

        if (writer == null) {
            return;
        }

        if (context == null) {
            context = JAXBContext.newInstance(object.getClass());
        }

        Marshaller marshaller = context.createMarshaller();
        if (properties != null) {
            Iterator<Map.Entry<String, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                marshaller.setProperty(entry.getKey(), entry.getValue());
            }
        }
        marshaller.marshal(object, writer);
    }

    /**
     * 序列化
     *
     * @param context
     * @param object
     * @param properties
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    public static String marshal(JAXBContext context, Object object, Map<String, Object> properties) throws
            JAXBException {
        StringWriter writer = new StringWriter();
        marshal(context, object, writer, properties);
        return writer.toString();
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    public static String marshal(Object object) throws JAXBException {
        StringWriter writer = new StringWriter();
        marshal(null, object, writer, null);
        return writer.toString();
    }

    public static void main(String[] args) {
        try {

            Employee employee = new Employee("anzhen","anan");
            List<Employee.Job > jobs = new ArrayList<Employee.Job>();
            Employee.Job job = new Employee.Job(1,"teacher");
            jobs.add(job);
            employee.setJobs(jobs);
            Map<String ,Object> map = new HashMap<String, Object>();
            map.put(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            String source = marshal(null,employee,map);
            Employee ee =  unmarshal(Employee.class,source);
            System.out.println();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @XmlRootElement(name = "employ", namespace = "www.wang.com")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Employee{
        private String firstName;
        private String secondName;

        @XmlElementWrapper
        @XmlElement(name = "job")
        private List<Job> jobs;

        public List<Job> getJobs() {
            return jobs;
        }

        public void setJobs(List<Job> jobs) {
            this.jobs = jobs;
        }

        Employee() {
        }

        Employee(String firstName, String secondName) {
            this.firstName = firstName;
            this.secondName = secondName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        static class Job{

            private int id;
            private String name;

            Job() {
            }

            Job(int id, String name) {
                this.id = id;
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }

}
