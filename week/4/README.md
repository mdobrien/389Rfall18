Pentesting I
======

## Assignment details

This assignment has two parts. It is due by 9/27 at 11:59 PM.
To submit your homework, please follow the guidelines on the front page to edit the README in the /writeup folder and push your completed work to GitHub.


**There will be a late penalty of 5% off per day late!**

### Part 1

Fred Krueger has implemented a new uptime system for Cornerstone Airlines! He plans to use this system as a means to test the uptime of Internet-connected devices behind the company's network. Without a doubt, Fred claims that his new solution is inherently secure because it utilizes the Linux ping command to do the uptime checking instead of using a web-based OSINT solution.

However, there are rumors that Freds's new service is vulnerable to a Command Injection attack. Can you prove to Fred that his new uptime system isn't as secure as he claims? If so, go and get the flag!

`nc cornerstoneairlines.co 45`

For full credit, write up (step-by-step) how you got the flag and what Fred should do to protect from this vulnerability.

---- Response ----
Flag path: /home/flag.txt
Flag: p1ng_as_a_$erv1c3
Script path: /opt/container_startup.sh
Command Injection: "; cat /home/flag/txt"
---- Response ----

cho "Network Administration Panel -- Uptime Monitor "
echo -n "Enter IP address: " read input >&2 echo "[$(date)] INPUT: $input" cmd="ping -w 5 -c 2 $input" output=$(eval $cmd) echo $output

* Describing your thought process.
When it said we are using a mixture of input from a using and ping my first thought was that it is likely a command injection vulnerability. I typed in an IP address followed by a semi-colon and the ls command to test my idea. It worked, so my next thought was i need to find the target data. I ran the command inject and displayed the contents of the /home directory. My though process was to see what users are on the machine to give me more information to use. Also users are generally the biggest threats to a system. After I found the flag, i began looking for the script. Since the script can be ran at any point, it thought that it must be running. I ran another command injection "; ps -ef" to get a list of all the proccesses running on the server. This command returned a proccess running at /opt/container_startup.sh. This was the only proccess ruinng, so i thought that it must be the Uptime Script. I excuted another command inject to print the contents of /opt/container_startup.sh. My thought was correct because it returned the code for my script of interest.


* Any suggested precautions Fred could implement to prevent this vulnerability (hint: can you find the script that Fred uses to check the uptime?)
Fred committed a security cardinal sin by directly passing using input as the parameter for a system command. Fred has two options to santize the data by escaping known any input that does not fit an IP address or to reject input if it contains malformed data. If I were fred, I would implement the regular expression to match the input against. If the input does not pass, then reject the input and don't execute the command.

### Part 2

Working through this script to exploit the command injection vulnerabilty had three parts.

The first task was scripting the command injection. I had to establish a socket connection to send and receive data from the cornerstoneairlines.co.

The next task was building the interactive shell. I built the structure to handle the given shell commands (shell, help, pull, and quit). I implemented help and quit. The next challenge was to implement the pull functionality. This incorporated parsing the input for file paths, and then reading and writing to and from those locations.

The last part was to develop the mini command injection shell. The most difficult part of this retaining the cwd after a command is excuted. Due the one time connection interaction with the server. The current working directory had to be stored locally, and then appending to file paths as need for the ls, cd, and cat commands.


Using the provided stub code, implement an interactive shell that leverages the above vulnerability.

You want to be able to conduct the following actions (by calling their respective commands) in this shell:

1) `shell`                               Drop into an interactive shell and allow users to gracefully `exit`
2) `pull <remote-path> <local-path>`     Download files
3) `help`                                Shows this help menu
4) `quit`                                Quit the shell

If any malformed input occurs, be sure to show this usage function.

Note: If you choose to write your own program in another language, please include instructions on how to execute your program including what version of the language you are using. Please add this detail to a README.txt or README.md file. You will NOT receive credit if the TAs cannot run your program.

Note: If you are stuck on this part of the assignment, please let us know. The facilitator staff is open to releasing hints, though we reserve the right to deny releasing specific hints if we deem it appropriate.

Note: Here's a [screenshot](shellimg.png) of what we roughly expect. I'll post a simple "public test" for which you will be graded on. Kindly bear with us here, we're trying something new :)
