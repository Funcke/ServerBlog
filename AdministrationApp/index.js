#!/usr/bin/env node

var amqp = require('amqplib/callback_api');
amqp.connect('amqp://ixwqfxak:CzfZYkg_H6Gov7kXv8R9FeWRpUz7Znh6@stingray.rmq.cloudamqp.com/ixwqfxak', function(error0, connection) {
  if (error0) {
    throw error0;
  }
  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }
    var exchange = 'administration';

    channel.assertExchange(exchange, 'topic', {
      durable: false
    });

    channel.assertQueue('', {
      exclusive: true
    }, function(error2, q) {
      if (error2) {
        throw error2;
      }
      console.log(' [*] Waiting for logs. To exit press CTRL+C');

        channel.bindQueue(q.queue, exchange, 'users');

      channel.consume(q.queue, function(msg) {
        console.log(" [x] %s:'%s'", msg.fields.routingKey, msg.content.toString());
      }, {
        noAck: true
      });
    });
  });
});
