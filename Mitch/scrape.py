#!/usr/bin/python

#I have this script checking that s&t spreadsheet every minute, and logging when/how it changes so we can test its true accuracy

from datetime import datetime
import requests

response = requests.get('https://docs.google.com/spreadsheets/d/1A0s8AJ8pLgPt929itg61E4SkB0HlZg8ofgiMyLZtbo8/edit?usp%5Cu003ddrive_web%5Cu0026amp;headers%5Cu003d1#gid=0&range=B11')
assert response.status_code == 200, 'Wrong status code'

raw = str(response.content)
start = raw.find("Fitness Center") + 15
current = ""

while raw[start].isdigit():
    current += raw[start]
    start += 1

now = datetime.now()
current_time = now.strftime("%D:%H:%M:%S")

path_to_log = "/home/mitchhit234/git/JIMSS/Mitch/log.txt"

with open(path_to_log, "r") as g:
    first_line = g.readline()
    for last_line in g:
        pass

i = 0
match = ""
while last_line[i].isdigit():
    match += last_line[i]
    i += 1

if match != current:
    f = open(path_to_log, "a")
    f.write(current + ", Time = " + current_time + '\n')
    f.close()
