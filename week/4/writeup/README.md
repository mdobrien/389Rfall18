Writeup 3 - Pentesting I
======

Name: *PUT YOUR NAME HERE*
Section: *PUT YOUR SECTION HERE*

I pledge on my honor that I have not given or received any unauthorized assistance on this assignment or examination.

Digital acknowledgement of honor pledge: *PUT YOUR NAME HERE*

## Assignment 4 Writeup

### Part 1 (45 pts)

Flag path: /home/flag.txt
Flag: p1ng_as_a_$erv1c3
Script path: /opt/container_startup.sh
Command Injection: "; cat /home/flag/txt"

When it said we are using a mixture of input from a using and ping my first thought was that it is likely a command injection vulnerability. I typed in an IP address followed by a semi-colon and the ls command to test my idea. It worked, so my next thought was i need to find the target data. I ran the command inject and displayed the contents of the /home directory. My though process was to see what users are on the machine to give me more information to use. Also users are generally the biggest threats to a system. After I found the flag, i began looking for the script. Since the script can be ran at any point, it thought that it must be running. I ran another command injection "; ps -ef" to get a list of all the processes running on the server. This command returned a process running at /opt/container_startup.sh. This was the only process ruining, so i thought that it must be the Uptime Script. I executed another command inject to print the contents of /opt/container_startup.sh. My thought was correct because it returned the code for my script of interest.

Fred committed a security cardinal sin by directly passing using input as the parameter for a system command. Fred has two options to sanitize the data by escaping known any input that does not fit an IP address or to reject input if it contains malformed data. If I were fred, I would implement the regular expression to match the input against. If the input does not pass, then reject the input and don't execute the command.

### Part 2 (55 pts)

Working through this script to exploit the command injection vulnerability had three parts.

The first task was scripting the command injection. I had to establish a socket connection to send and receive data from the cornerstoneairlines.co.

The next task was building the interactive shell. I built the structure to handle the given shell commands (shell, help, pull, and quit). I implemented help and quit. The next challenge was to implement the pull functionality. This incorporated parsing the input for file paths, and then reading and writing to and from those locations.

The last part was to develop the mini command injection shell. The most difficult part of this retaining the current working directory after a command is executed. Due the one time connection interaction with the server. The current working directory had to be stored locally, and then appending to file paths as need for the ls, cd, and cat commands.
