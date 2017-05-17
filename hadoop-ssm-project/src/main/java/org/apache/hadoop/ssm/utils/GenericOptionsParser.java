/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.ssm.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Shell;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GenericOptionsParser {
  private Configuration conf;
  private CommandLine commandLine;

  /**
   * Create an options parser with the given options to parse the args.
   * @param opts the options
   * @param args the command line arguments
   * @throws IOException
   */
  public GenericOptionsParser(Options opts, String[] args)
      throws IOException {
    this(new Configuration(), opts, args);
  }

  /**
   * Create an options parser to parse the args.
   * @param args the command line arguments
   * @throws IOException
   */
  public GenericOptionsParser(String[] args)
      throws IOException {
    this(new Configuration(), new Options(), args);
  }

  /**
   * Create a <code>GenericOptionsParser<code> to parse only the generic Hadoop
   * arguments.
   *
   * The array of string arguments other than the generic arguments can be
   * obtained by {@link #getRemainingArgs()}.
   *
   * @param conf the <code>Configuration</code> to modify.
   * @param args command-line arguments.
   * @throws IOException
   */
  public GenericOptionsParser(Configuration conf, String[] args)
      throws IOException {
    this(conf, new Options(), args);
  }

  /**
   * Create a <code>GenericOptionsParser</code> to parse given options as well
   * as generic Hadoop options.
   *
   * The resulting <code>CommandLine</code> object can be obtained by
   * {@link #getCommandLine()}.
   *
   * @param conf the configuration to modify
   * @param options options built by the caller
   * @param args User-specified arguments
   * @throws IOException
   */
  public GenericOptionsParser(Configuration conf,
      Options options, String[] args) throws IOException {
    this.conf = conf;
    parseGeneralOptions(options, args);
  }

  /**
   * Returns an array of Strings containing only application-specific arguments.
   *
   * @return array of <code>String</code>s containing the un-parsed arguments
   * or <strong>empty array</strong> if commandLine was not defined.
   */
  public String[] getRemainingArgs() {
    return (commandLine == null) ? new String[]{} : commandLine.getArgs();
  }

  /**
   * Get the modified configuration
   * @return the configuration that has the modified parameters.
   */
  public Configuration getConfiguration() {
    return conf;
  }

  /**
   * Returns the commons-cli <code>CommandLine</code> object
   * to process the parsed arguments.
   *
   * Note: If the object is created with
   * {@link #GenericOptionsParser(Configuration, String[])}, then returned
   * object will only contain parsed generic options.
   *
   * @return <code>CommandLine</code> representing list of arguments
   *         parsed against Options descriptor.
   */
  public CommandLine getCommandLine() {
    return commandLine;
  }

  /**
   * Specify properties of each generic option
   */
  @SuppressWarnings("static-access")
  private static synchronized Options buildGeneralOptions(Options opts) {
    Option property = OptionBuilder.withArgName("property=value")
        .hasArg()
        .withDescription("use value for given property")
        .create('D');
    opts.addOption(property);

    return opts;
  }

  /**
   * Modify configuration according user-specified generic options.
   *
   * @param line User-specified generic options
   */
  private void processGeneralOptions(CommandLine line) throws IOException {
    if (line.hasOption('D')) {
      String[] property = line.getOptionValues('D');
      for(String prop : property) {
        String[] keyval = prop.split("=", 2);
        if (keyval.length == 2) {
          conf.set(keyval[0], keyval[1], "from command line");
        }
      }
    }
  }

  /**
   * Windows powershell and cmd can parse key=value themselves, because
   * /pkey=value is same as /pkey value under windows. However this is not
   * compatible with how we get arbitrary key values in -Dkey=value format.
   * Under windows -D key=value or -Dkey=value might be passed as
   * [-Dkey, value] or [-D key, value]. This method does undo these and
   * return a modified args list by manually changing [-D, key, value]
   * into [-D, key=value]
   *
   * @param args command line arguments
   * @return fixed command line arguments that GnuParser can parse
   */
  private String[] preProcessForWindows(String[] args) {
    if (!Shell.WINDOWS) {
      return args;
    }
    if (args == null) {
      return null;
    }
    List<String> newArgs = new ArrayList<String>(args.length);
    for (int i=0; i < args.length; i++) {
      String prop = null;
      if (args[i].equals("-D")) {
        newArgs.add(args[i]);
        if (i < args.length - 1) {
          prop = args[++i];
        }
      } else if (args[i].startsWith("-D")) {
        prop = args[i];
      } else {
        newArgs.add(args[i]);
      }
      if (prop != null) {
        if (prop.contains("=")) {
          // everything good
        } else {
          if (i < args.length - 1) {
            prop += "=" + args[++i];
          }
        }
        newArgs.add(prop);
      }
    }

    return newArgs.toArray(new String[newArgs.size()]);
  }

  /**
   * Parse the user-specified options, get the generic options, and modify
   * configuration accordingly.
   *
   * @param opts Options to use for parsing args.
   * @param args User-specified arguments
   */
  private void parseGeneralOptions(Options opts, String[] args)
      throws IOException {
    opts = buildGeneralOptions(opts);
    CommandLineParser parser = new GnuParser();
    try {
      commandLine = parser.parse(opts, preProcessForWindows(args), true);
      processGeneralOptions(commandLine);
    } catch(ParseException e) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("general options are: ", opts);
    }
  }

  /**
   * Print the usage message for generic command-line options supported.
   *
   * @param out stream to print the usage message to.
   */
  public static void printGenericCommandUsage(PrintStream out) {
    out.println("Generic options supported are:");
    out.println("-D <property=value>               "
        + "define a value for a given property");
    out.println();
    out.println("The general command line syntax is:");
    out.println("command [genericOptions] [commandOptions]");
    out.println();
  }

}
