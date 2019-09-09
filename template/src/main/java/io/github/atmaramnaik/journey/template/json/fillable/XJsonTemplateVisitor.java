package io.github.atmaramnaik.journey.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.TBaseVisitor;
import io.github.atmaramnaik.journey.template.TParser;
import io.github.atmaramnaik.journey.template.Template;
import io.github.atmaramnaik.journey.template.expression.VariableExpression;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.*;
import io.github.atmaramnaik.journey.template.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XJsonTemplateVisitor extends TBaseVisitor<ExtractableJsonTemplate<Json>> {
    @Override
    public ExtractableJsonTemplate<Json> visitXJson(TParser.XJsonContext ctx) {
        return visitXValue(ctx.xValue());
    }

    @Override
    public ExtractableJsonTemplate<Json> visitXValue(TParser.XValueContext ctx) {
        if(ctx.xObj() != null){
            return visitXObj(ctx.xObj());
        }
        if(ctx.xArray() != null){
            return visitXArray(ctx.xArray());
        }
        if(ctx.xDynamicArray() !=null){
            return visitXDynamicArray(ctx.xDynamicArray());
        } if (ctx.xExpression() != null){
            return visitXExpression(ctx.xExpression());
        }
        return null;
    }

    @Override
    public ExtractableJsonTemplate<Json> visitXObj(TParser.XObjContext ctx) {
        HashMap<Text,ExtractableJsonTemplate<Json>> pairs=new HashMap<>();
        for (TParser.XPairContext xpair:
             ctx.xPair()) {
            if(xpair.STRING()!=null){
                pairs.put(Template.text(Template.string(xpair.STRING().getText().replaceAll("^\"|\"$", ""))),visitXValue(xpair.xValue()));
            } else {
                //To Do For Fillable Templates
            }
        }
        return new ExtractableJsonObjectTemplate(pairs);
    }

    @Override
    public ExtractableJsonTemplate<Json> visitXArray(TParser.XArrayContext ctx) {
        List<ExtractableJsonTemplate<Json>> list=new ArrayList<>();
        for (TParser.XValueContext xValueContext:
                ctx.xValue()) {
            list.add(visitXValue(xValueContext));
        }
        return new ExtractableJsonArrayTemplate(list);
    }

    @Override
    public ExtractableJsonTemplate<Json> visitXDynamicArray(TParser.XDynamicArrayContext ctx) {
        return new ExtractableDynamicJsonArrayTemplate(ctx.name(0).IDENTIFIER().getText(),visitXValue(ctx.xValue()));
    }

    @Override
    public ExtractableJsonTemplate<Json> visitXExpression(TParser.XExpressionContext ctx) {
        VariableExpression variableExpression = Template.var(ctx.expressionVariable().name().IDENTIFIER().getText());
        if(ctx.expressionVariable().type() !=null){
            String type = ctx.expressionVariable().type().name().IDENTIFIER().getText();
            for (Class key:
                    ValueHolder.valueHolderMap.keySet()) {
                if(key.getSimpleName().equals(type)){
                    variableExpression.ofType(key);
                    return new ExtractableVariableExpressionTemplate(variableExpression);
                }
            }
            for (Class key:
                    ValueHolder.fallbackValueHolderMap.keySet()) {
                if(key.getSimpleName().equals(type)){
                    variableExpression.ofType(key);
                    return new ExtractableVariableExpressionTemplate(variableExpression);
                }
            }

        }
        return new ExtractableVariableExpressionTemplate(variableExpression);
    }
}
