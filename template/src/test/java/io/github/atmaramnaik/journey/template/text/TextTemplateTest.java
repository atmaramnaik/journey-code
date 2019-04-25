package io.github.atmaramnaik.journey.template.text;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.template.text.TextTemplate;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static io.github.atmaramnaik.journey.core.io.Helper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static io.github.atmaramnaik.journey.template.Template.*;

public class TextTemplateTest {
    @Test
    public void shouldFillTextTemplateWithSingleBlock() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        MockIO mockIO=aMockIO("123",byteArrayOutputStream);
        TextTemplate textTemplate=text(textEx(var("var").ofType(Integer.class)));
        Context context=new Context();
        String finalVal=textTemplate.process(context,mockIO).getValue();
        assertThat(finalVal).isEqualTo("123");
    }
    @Test
    public void shouldFillTextTemplateWithMultipleBlock() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        MockIO mockIO=aMockIO("123\nHello\n",byteArrayOutputStream);
        TextTemplate textTemplate=text(
                string("Start"),
                textEx(var("Age")
                        .ofType(Integer.class)
                )
                ,textEx(var("Name")
                        .ofType(String.class)));

        Context context=new Context();
        String finalVal=textTemplate.process(context,mockIO).getValue();
        assertThat(finalVal).isEqualTo("Start123Hello");
        String data=new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        assertThat(data).isEqualTo("Please provide value for Age of type Integer\n" +
                "Please provide value for Name of type String\n");
    }
    @Test
    public void shouldFillTextTemplateWithLoopBlock() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        MockIO mockIO=aMockIO("2\n12\nAtmaram\n32\nYogesh",byteArrayOutputStream);
        TextTemplate textTemplate=text(
                textLoop("persons").with(
                        textEx(
                                var("Age")
                                        .ofType(Integer.class))
                ).with(
                        textEx(
                                var("Name")
                                        .ofType(String.class))
                )
        );

        Context context=new Context();
        String finalVal=textTemplate.process(context,mockIO).getValue();
        assertThat(finalVal).isEqualTo("12Atmaram32Yogesh");
        String data=new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        assertThat(data).isEqualTo("Please provide value for persons\n" +
                "Enter size of list as Integer\n" +
                "Please provide value for Age of type Integer\n" +
                "Please provide value for Name of type String\n" +
                "Please provide value for Age of type Integer\n" +
                "Please provide value for Name of type String\n");
    }
    @Test
    public void shouldFillTextTemplateWithChoiceBlock() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        MockIO mockIO=aMockIO("2\n",byteArrayOutputStream);

        HashMap<String,Object> data=new HashMap<>();
        HashMap<String,Object> person1=new HashMap<>();
        person1.put("Age",32);
        person1.put("Name","Atmaram");

        HashMap<String,Object> person2=new HashMap<>();
        person2.put("Age",3);
        person2.put("Name","Atiksh");

        List<HashMap<String,Object>> list=new ArrayList<>();
        list.add(person1);
        list.add(person2);

        data.put("persons",list);

        TextTemplate textTemplate=text(
                choose("persons","Name",textEx(
                        var("Age")
                                .ofType(Integer.class)))
        );


        Context context=new Context(data);

        String finalVal=textTemplate.process(context,mockIO).getValue();
        assertThat(finalVal).isEqualTo("3");
        String resp=new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        assertThat(resp).isEqualTo("1) Atmaram\n" +
                "2) Atiksh\n" +
                "Input your choice  of type Integer\n");
    }
}
