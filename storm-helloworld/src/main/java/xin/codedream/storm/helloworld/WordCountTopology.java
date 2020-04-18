package xin.codedream.storm.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 单词技术拓扑
 *
 * @author LeiXinXin
 * @date 2020/4/17$
 */
@Slf4j
public class WordCountTopology {
    /**
     * spout，继承一个基类，实现接口，这个主要是负责从数据源获取数据
     * <p>
     * 这里做了一个简化，就不从外部去获取数据了，从内部不断发射一些句子
     */
    public static class RandomSentenceSpout extends BaseRichSpout {

        private SpoutOutputCollector collector;
        private ThreadLocalRandom localRandom;

        /**
         * open方法，是对spout进行初始化的
         * <p>
         * 比如创建线程池、或者创建数据库连接池之类的，一些池化的工作都可以在这里做
         *
         * @param map
         * @param topologyContext
         * @param spoutOutputCollector
         */
        @Override
        public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
            // 在open方法初始化的时候，会传入一个叫做spoutOutputCollector，是用来发射数据出去的
            this.collector = spoutOutputCollector;
            this.localRandom = ThreadLocalRandom.current();
        }

        /**
         * 这个spout类，最终会运行在task中，某个worker进程的某个executor线程内部的某个task中的那个task会负责，
         * 不断的无限循环调用nextTuple()方法
         * 无限循环调用，可以不断发射最新的数据出去，形成一个数据流
         */
        @Override
        public void nextTuple() {
            Utils.sleep(100);
            String[] sentences = new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
                    "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature"};
            final String sentence = sentences[localRandom.nextInt(sentences.length)];

            log.info("[发射句子] sentence:{}", sentence);
            collector.emit(new Values(sentence));
        }

        /**
         * declareOutputFields 方法，这个方法定义一个你发射出去的每个tuple中的每个 field 的名称是什么
         *
         * @param outputFieldsDeclarer
         */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("sentence"));
        }
    }


    /**
     * 写一个bolt直接继承一个BaseRichBolt的基类
     */
    public static class SplitSentence extends BaseRichBolt {
        private OutputCollector collector;

        @Override
        public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        /**
         * 每次接收到一条数据后，就会交给execute方法来执行
         *
         * @param input
         */
        @Override
        public void execute(Tuple input) {
            String sentence = input.getStringByField("sentence");
            String[] words = sentence.split(" ");
            for (String word : words) {
                collector.emit(new Values(word));
            }
        }

        /**
         * 定义发射出去的tuple，每个field的名称
         *
         * @param declarer
         */
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }
    }

    public static class WordCount extends BaseRichBolt {
        private final Map<String, Long> wordCounts = new HashMap<>();
        private OutputCollector collector;

        @Override
        public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple input) {
            String word = input.getStringByField("word");
            wordCounts.merge(word, 1L, Long::sum);
            Long count = wordCounts.get(word);
            log.info("[统计单词计数]单词：{}, 出现次数：{}", word, count);
            collector.emit(new Values(word, count));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }
}
