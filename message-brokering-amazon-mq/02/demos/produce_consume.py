import pika
import ssl

AMQP_URL = 'amqp://b-390c347c-c948-4ef1-ae46-b125a13e572d-1.mq.us-west-1.amazonaws.com:5671'
QUEUE_NAME = 'AppQueue'
USERNAME = 'bharath'
PASSWORD = 'Test@12345'

def get_connection():
    # Set up SSL context
    ssl_context = ssl.create_default_context()
    ssl_context.verify_mode = ssl.CERT_NONE
    ssl_context.check_hostname = False  # Disable hostname checking

    # Set up connection parameters
    parameters = pika.ConnectionParameters(
        host='b-390c347c-c948-4ef1-ae46-b125a13e572d-1.mq.us-west-1.amazonaws.com',
        port=5671,
        virtual_host='/',
        credentials=pika.PlainCredentials(USERNAME, PASSWORD),
        ssl_options=pika.SSLOptions(context=ssl_context)
    )

    connection = pika.BlockingConnection(parameters)
    return connection

def produce(connection):
    channel = connection.channel()
    channel.queue_declare(queue=QUEUE_NAME)

    message = "Hello from Amazon MQ!"
    channel.basic_publish(exchange='', routing_key=QUEUE_NAME, body=message)
    print(f"Message sent: {message}")

def consume(connection):
    channel = connection.channel()
    channel.queue_declare(queue=QUEUE_NAME)

    def callback(ch, method, properties, body):
        print(f"Message received: {body.decode()}")

    channel.basic_consume(queue=QUEUE_NAME, on_message_callback=callback, auto_ack=True)

    print('Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()

if __name__ == "__main__":
    connection = get_connection()
    try:
        produce(connection)
    #        consume(connection)
    finally:
        connection.close()
        print("Exiting app")
