from urllib.request import urlopen

url="https://sites.google.com/a/mst.edu/src-mobile-site/home/live-stats"
page = urlopen(url)

html_bytes = page.read()
html = html_bytes.decode("utf-8")


f = open("output.txt", "w")
f.write(html)
f.close()
