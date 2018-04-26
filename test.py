from google import google
import tldextract
import csv
import sys
import time

try:
	with open('/Users/kelvin/Dropbox/crypto2018uh/ism2018.csv', 'r') as csvfile:
		csvreader = csv.reader(csvfile)
		next(csvreader)
		for row in csvreader:
			name = unicode(row[0], 'utf-8')
			company_name = unicode(row[2], 'utf-8')
			num_page = 3
			time.sleep(15)
			search_results = google.search(company_name, num_page)
			extracted = tldextract.extract(search_results[0].link)
			domain = "{}.{}".format(extracted.domain, extracted.suffix)
			#print name
			#print ','
			print domain
except Exception as e: print(e)

