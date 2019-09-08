package io.github.atmaramnaik.journey.kotlin
import io.github.atmaramnaik.journey.template.Template.`object`
import io.github.atmaramnaik.journey.template.Template.array
import io.github.atmaramnaik.journey.template.json.fillable.JsonArrayTemplate
import io.github.atmaramnaik.journey.template.json.fillable.JsonObjectTemplate
fun obj(block: JsonObjectTemplate.()->Unit):JsonObjectTemplate{
    var objt = `object`();
    objt.block()
    return objt;
}
fun array(block: JsonArrayTemplate.() -> Unit):JsonArrayTemplate{
    var arr = array()
    arr.block()
    return arr
}