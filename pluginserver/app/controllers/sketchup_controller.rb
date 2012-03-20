include Java

require './voice.jar'

class SketchupController < ApplicationController
  def automate
    puts 'Boo1'
    obj = Java::TestListen.new
    obj.StartListening()
    puts 'Boo2'
  end
end
