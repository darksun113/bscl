#given is the result from test4.py, output is list of names and companies
fr=open('sampleal_res.txt','r')
lines=fr.readlines()
for line in lines:
	token=line.split('|||')
	if len(token) < 2:
		continue
	name=token[0].split(' - ')[0]
	token2=token[1].split('\t')
	company=token2[0].strip(' \t\n\r')
	if len(token2) > 1:
		company=token2[1]
	print("\""+name+"\",\""+company+"\"")