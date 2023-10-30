#!/usr/bin/env jruby
require 'pry'
require 'json'
$LOAD_PATH.unshift(__dir__) unless $LOAD_PATH.include?(__dir__)
app_fat_jar = Dir["#{__dir__}/../target/mercury*.jar"].first || raise("This script needs Spring Boot fat JAR based app available in target/")
lib_jars = Dir["#{app_fat_jar}!/BOOT-INF/lib/*.jar"]
raise("No Spring Boot libraries found in #{app_fat_jar}") if lib_jars.empty?
lib_jars.sort.each {|jar| require jar}
$CLASSPATH << "#{__dir__}/../target/classes"

module Kernel
  def pl
    JavaUtilities.get_package_module_dot_format('pl') # stub
  end
end

if ARGV.empty?
  puts("Usage: #{$0} <file_path>")
  exit 1
end

config = pl.amitec.mercury.util.StructUtils.propertiesFileToMap(ARGV[0])
client = pl.redbay.ws.client.RedbayCxfClient.new(config["redbay.url"], true);
api = client.getAPI()
ticket = api.createTicket(config["redbay.service_key"],config["redbay.app_id"].to_i,config["redbay.auth_pass"])
binding.pry
