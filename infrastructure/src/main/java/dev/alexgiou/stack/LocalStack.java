package dev.alexgiou.stack;

import software.amazon.awscdk.*;

/**
 * @author: Alexandros Giounan
 * @code @created: 7/27/2025
 */

public class LocalStack extends Stack {
    public LocalStack(final App scope, final String id, final StackProps props) {
        super(scope, id, props);
    }

    public static void main(final String[] args) {
        App app = new App(AppProps.builder().outdir("./cdk.out").build());

        StackProps props = StackProps.builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build();

        new LocalStack(app, "LocalStack", props);
        app.synth();
        System.out.println("App synthesizing in progress...");
    }
}
