#!/usr/bin/env -S jruby -J--add-opens -Jjava.base/sun.nio.ch=ALL-UNNAMED -J--add-opens -Jjava.base/java.io=ALL-UNNAMED
require 'pry'
require 'json'
$LOAD_PATH.unshift(__dir__) unless $LOAD_PATH.include?(__dir__)
begin
  $CLASSPATH << File.read("#{__dir__}/../target/dev-classpath.txt").split(":")
rescue => e
  $stderr.puts e
  $stderr.puts "Run at least `mvn compile` to generate classes"
  exit(1)
end
$CLASSPATH << "#{__dir__}/../target/classes"

module Kernel
  def pl
    JavaUtilities.get_package_module_dot_format('pl') # stub
  end
end
module RedbayClientScope
  # Works only in module
  include_package 'pl.redbay.ws.client'
  include_package 'pl.redbay.ws.client.types'
  java_import 'java.time.LocalDateTime'

  def client(config_path)
    config_props = java.util.Properties.new
    config_props.load(java.io.FileReader.new(config_path))
    config = config_props.to_h

    client = pl.redbay.ws.client.RedbayCxfClient.new(config["redbay.url"], true);
    api = client.getAPI()
    ticket = api.createTicket(config["redbay.service_key"],config["redbay.app_id"].to_i,config["redbay.auth_pass"])
    puts "Variable: ticket='#{ticket}'"
    puts "'api' methods: #{api.methods}"

    # Interactivity
    binding.pry
  end
end

class RedbayCli
  include RedbayClientScope
end


if ARGV.empty?
  puts("Usage: #{$0} <file_path>")
  exit 1
end

RedbayCli.new.client(ARGV[0])

