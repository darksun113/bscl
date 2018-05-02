#given is a list of keywords containing job tiltles and companies, output is a list of names

from google import google
import time
import re

import sys
import codecs
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
sys.stdout = codecs.getwriter('utf8')(sys.stdout)
sys.stderr = codecs.getwriter('utf8')(sys.stderr)
try:
	with open('oil-gas-logistics.txt') as f:
		f2 = open('testfile.txt','w')
			content = f.readlines()
			num_page = 1
			for line in content:
				time.sleep(15)
					search_results = google.search(line, num_page)
					for result in search_results:
						matchObj = re.match(r'[^,]*\s[^,]*\s{1,1}-\s{1,1}.*\s{1,1}-\s{1,1}.*', result.name, re.M|re.I)
							if matchObj:
								str1 = result.name.decode("utf-8", errors='ignore')
								print(str1)
								break;
except Exception as e: print(e)