#!/usr/bin/env python
# -*- coding: utf8 -*-
from pyhunter import PyHunter
import json
import csv
import sys
import clearbit

#API Key
hunter = PyHunter('016a2e85a4fd5bc142c055c42cb60954fc1710df')


#option 1:
try:
	with open('/Users/kelvin/Desktop/ism2018.csv', 'r', encoding='utf-8') as csvfile:
		csvreader = csv.reader(csvfile)
		next(csvreader)
		for row in csvreader:
	    	#extracting the name and company name from the csv file
			"""
			#if list has first and last name:
			first_name = row[0]
			last_name = row[1]
			name = row[0] + " " + row[1]
			company_name = row[2] 
			"""
			name = row[0].encode('utf8')
			name_tokens = name.split()
			domain = row[3]
			if "facebook" in domain:
				continue
			#sys.stdout.buffer.write(name)
			#print(":")
			#print(domain)
	    	#printing the results:
			email, confidence_score = hunter.email_finder(domain, full_name=name)
			print(email)
except:
	print("ERROR in option 1", sys.infp())
