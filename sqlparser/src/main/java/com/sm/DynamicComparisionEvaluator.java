package com.sm;

import org.graalvm.polyglot.*;;

public class DynamicComparisionEvaluator {
    
   private final String condition;


    public DynamicComparisionEvaluator(String condition) {
        this.condition = condition;
    }

    public boolean  evaluate() throws Exception {
        
        try (Context context = Context.newBuilder("js").allowAllAccess(true).build()) {
            context.eval("js", String.format("function evaluate() { return %s; }", condition));
            Value evaluate = context.getBindings("js").getMember("evaluate");
            boolean isValid = evaluate.execute().asBoolean();
            System.out.println("Is condition valid: " + isValid);
            return isValid;
        }
    }

}
