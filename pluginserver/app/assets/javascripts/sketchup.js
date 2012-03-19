// Place all the behaviors and hooks related to the matching controller here.
// All this logic will automatically be available in application.js.
// You can use CoffeeScript in this file: http://jashkenas.github.com/coffee-script/

CONTROLLER = { "count": 0 };

function callRuby(actionName) {
  query = 'skp:implement@' + actionName;
  window.location.href = query;
}

function checkForCommand() {
  $("#output").text("Polling for commands");
  $.jsonp({
    url: "http://localhost:3000/",
    dataType: "jsonp",
    callbackParameter: "jsonCallback",
    timeout: 1000,
    success: function(data, status){
      if (data.error) {
        $("#output").text(data);
      }
      else {
        callRuby('do an action');
      }
    },
    error: function(XHR, textStatus, errorThrown){
      $("#output").text('Generic jsonp error/Unexpected Error! :)');
    }
  });
}

$(function() {
  // Setup websocket
  wsUri = "ws://echo.websocket.org/";
  var websocket = new WebSocket(wsUri);
  websocket.onopen = function(evt) {
    console.log("socket is open");
    $("#output").text("Connected to Server");
  };
  websocket.onclose = function(evt) {
    console.log("socket is closed");
    $("#output").text("Disconnected from Server");
  };
  websocket.onmessage = function(evt) {
    console.log("socket got message: " + evt.data);
    $("#output").text("Server sent message: " + evt.data);
  };
  websocket.onerror = function(evt) {
    console.log("socket got error: " + evt.data);
    $("#output").text("Socket error: " + evt.data);
  };
});
