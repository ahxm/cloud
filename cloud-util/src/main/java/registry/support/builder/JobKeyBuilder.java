package registry.support.builder;

import registry.listener.ClusterListener;

/**
 * Created by anzhen on 2016/5/2.
 */
public class JobKeyBuilder implements KeyBuilder {

    protected String suffix;

    protected Class<?> jobClass;

    protected String key;


    public JobKeyBuilder(Class<?> jobClass, String suffix) {
        this.jobClass = jobClass;
        this.suffix = suffix;
    }

    @Override
    public String getKey() {

        if (key == null || key.isEmpty()) {
            String jobName = jobClass.getSimpleName();
            if (jobName.endsWith("Job")) {
                jobName = jobName.substring(0, jobName.length() - 3);
            }
            int count = 0;
            StringBuilder builder = new StringBuilder();
            char[] chars = jobName.toCharArray();
            for (char ch : chars) {
                count++;
                if (Character.isUpperCase(ch)) {
                    if (count > 1) {
                        builder.append(".");
                    }
                    builder.append(Character.toLowerCase(ch));
                } else {
                    builder.append(ch);
                }
            }
            builder.append(suffix);
            key = builder.toString();
        }
        return key;
    }

}