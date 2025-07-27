package dev.alexgiou.stack;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.Vpc;

/**
 * @author: Alexandros Giounan
 * @code @created: 7/27/2025
 */

public class LocalStack extends Stack {
    private final Vpc vpc;

    public LocalStack(final App scope, final String id, final StackProps props) {
        super(scope, id, props);
        this.vpc = createVpc();
    }

    private Vpc createVpc() {
        return Vpc.Builder.create(this, "PatientManagementVpc")
                .vpcName("PatientManagementVpc")
                .maxAzs(2)
                .build();
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
