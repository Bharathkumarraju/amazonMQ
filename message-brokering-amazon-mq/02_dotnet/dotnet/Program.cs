using System;
using Apache.NMS;
using Apache.NMS.Util;
using ActiveMQ;

namespace dotnet
{
    class Program
    {
        static string _endpoint = "activemq:tcp://b-bb8710fa-6f27-4113-958b-840b56789e8b-1.mq.us-east-2.amazonaws.com:61617";
        static string _queueName = "MyQueue";

        static void Main(string[] args)
        {
            var factory = new NMSConnectionFactory(new Uri(_endpoint));
            using (var connection = factory.CreateConnection())
            using (var session = connection.CreateSession())
            {
                connection.Start();

            }
        }
    }
}
