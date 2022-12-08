package org.chenxilin.flink.source;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.chenxilin.flink.produce.ProduceConfig;
import org.chenxilin.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * 循环文本源
 *
 * @author chenxilin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextCycleSource extends RichParallelSourceFunction<Map<String, Object>> {
    private static final long serialVersionUID = 211007289830285249L;
    private boolean isRunning = true;
    private ArrayList<String> jsonLines;
    private ProduceConfig config;

    public TextCycleSource(ArrayList<String> jsonLines, ProduceConfig config) {
        this.jsonLines = jsonLines;
        this.config = config;
    }

    @Override
    public void run(SourceContext<Map<String, Object>> ctx) {
        long count = 0;
        while (isRunning) {
            // 随机选择文件的一行
            String lineString = jsonLines.get(new Random().nextInt(jsonLines.size()));
            Map<String, Object> lineMap = (Map<String, Object>) JSON.parseObject(lineString, HashMap.class);

            // 添加时间
            if (config.isAddTime()) {
                String timeKey = config.getTimeKey();
                String timeValue = TimeUtil.timeStamp2str(System.currentTimeMillis(), config.getTimeFormat());
                lineMap.put(timeKey, timeValue);
            }

            // 添加自定义字段
            Map<String, Object> fields = (Map<String, Object>) JSON.parseObject(config.getAdditionalFields(), HashMap.class);
            for (String fieldKey : fields.keySet()) {
                lineMap.put(fieldKey, fields.get(fieldKey));
            }

            ctx.collect(lineMap);
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
