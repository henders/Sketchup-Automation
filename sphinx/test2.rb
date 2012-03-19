
server = open("/tmp/rubypipe", "r")
while server.gets
    puts "server said: #{$_}"
end
