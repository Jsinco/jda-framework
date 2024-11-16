A Framework to make working with the [JDA](https://github.com/discord-jda/JDA) wrapper easier.

Get a release from [JitPack](https://jitpack.io/#Jsinco/jda-framework).



# You'll still need to include JDA and a SLF4J implementation.

Here's what I recommend for your dependencies:

**Gradle (KTS):**
```kts
repositories {
    mavenCentral()
    maven("https://jitpack.io")

    //////////////////////////
    // OPTIONAL STUFF BELOW //
    //////////////////////////
    
    // If you're using Okaeri configs, include the repo
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
}

dependencies {
    implementation("com.github.Jsinco:jda-framework:%VERSION%")
    
    // JDA
    implementation("net.dv8tion:JDA:5.0.0-beta.24")

    // Logger
    // If you choose to use a logger other than Log4j, you'll need to disable
    // auto-logging configuration. See below*
    implementation("org.apache.logging.log4j:log4j-core:2.24.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.24.1")
    implementation("org.apache.logging.log4j:log4j-api:2.24.1")

    //////////////////////////
    // OPTIONAL STUFF BELOW //
    //////////////////////////
    
    // Google guava/gson
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("com.google.code.gson:gson:2.10.1")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Okaeri Configurations
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:5.0.5")
}
```

### To finish up your setup, declare your FrameWork startup.

```java

public static void main(String[] args) {
    FrameWork.start(Main.class);
}

// If you need to disable logging configuration, you'll need to provide full args.
// Here's an example you can use:

public static void main(String[] args) {
    Path dataFolderPath = FrameWork.getDefaultDataFolderPath("data"); // Or use whatever path you want
    String botTokenArgName = "bot_token"; // The bot token name that needs to be provided as an environment variable or property.
    FrameWork.start(Main.class, dataFolderPath, botTokenArgName, false);
}
```

### Finally, start your JDA by passing in a bot token as an environment variable or commandline property
```commandline
java -Dbot_token="<YOUR_BOT_TOKEN>" -jar MyJDADiscordBot.jar
```

# Now you can start using all the features of this Framework to build your Discord bot!

[See this repository](https://github.com/Jsinco/framework-example) for examples on how to use the features of this Framework.
