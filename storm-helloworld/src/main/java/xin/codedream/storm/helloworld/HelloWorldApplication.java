package xin.codedream.storm.helloworld;


import lombok.SneakyThrows;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * InventoryApplication
 *
 * @author LeiXinXin
 * @date 2020/3/25
 */
@SpringBootApplication(scanBasePackages = "xin.codedream.storm.helloworld")
public class HelloWorldApplication {


    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);

        TopologyBuilder topologyBuilder = new TopologyBuilder();

        // 这里的第一个参数，就是给spout设置一个名字
        // 第二个参数的意思，就是创建一个spout的对象
        // 第三个参数的意思，就是设置spout的executor有几个
        topologyBuilder.setSpout("RandomSentence", new WordCountTopology.RandomSentenceSpout(), 2);
        topologyBuilder.setBolt("SpiltSentence", new WordCountTopology.SplitSentence(), 5)
                .setNumTasks(10)
                .shuffleGrouping("RandomSentence");
        // 这个的意思就是说，相同的单词，从SpiltSentence发射出来时，一定会进入到下游的指定的，同一个task中
        // 只有这样，才能准确的统计出每个单词的数量
        topologyBuilder.setBolt("WordCount", new WordCountTopology.WordCount(), 10)
                .setNumTasks(20)
                .fieldsGrouping("SpiltSentence", new Fields("word"));

        Config config = new Config();

        // 说明是命令在执行，打算提交到storm集群上去
        if (args != null && args.length > 0) {
            config.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
        } else {
            // 说明正在ide上面运行
            config.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("WordCountTopology", config, topologyBuilder.createTopology());

            Utils.sleep(60000);

            cluster.shutdown();
        }
    }


}
