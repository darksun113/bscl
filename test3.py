#turns fullname into firstname and lastname
import csv
f1=open('/Users/kelvin/Dropbox/crypto2018uh/WPC2018-Registrants-19Mar18/final.csv','r')
f2=open('/Users/kelvin/Dropbox/crypto2018uh/WPC2018-Registrants-19Mar18/final_fl.csv','w')
f2.write('first_name,last_name,company\n')
csvreader = csv.reader(f1)
next(csvreader)
for row in csvreader:
	fullname=row[0]
	tokens=fullname.split(' ')
	if len(tokens) > 1 and len(tokens) < 3:
		firstname=tokens[0]
		lastname=tokens[1]
		if (firstname.find(',') == -1) and (lastname.find(',') == -1):
			domain=row[2]
			f2.write(firstname)
			f2.write(',')
			f2.write(lastname)
			f2.write(',')
			f2.write(domain)
			f2.write('\n')
f2.close()

