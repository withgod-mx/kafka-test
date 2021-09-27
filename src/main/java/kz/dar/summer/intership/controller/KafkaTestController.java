package kz.dar.summer.intership.controller;

import kz.dar.summer.intership.model.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    @Autowired
    KafkaTemplate<String, Object> template;


    @PostMapping
    public String kafkaProducerTest(@RequestBody HelloWorld hello) {

        template.send("summer-intership-test", hello);

        return "OK";
    }




}
