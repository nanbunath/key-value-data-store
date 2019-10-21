#This Application is developed using Spring MVC Framework
#If you want to give file path,Use http://localhost:8866/getpath?pathName={Here provide the encoded URI existing folderPath}
  it will automatically creates db.xlsx,keys.xlsx files under the given folder name
  
#Arbitary file path is created by  http://localhost:8866/createPath  - It will automatically create folder C://Directory2 and 
files db.xlsx,keys.xlsx

#Create operation of keyvalue done by calling http://localhost:8866/create - Find example JSON Body Format
<----Important!!! Provide TTL in seconds-------------->
[
	{
	"key":"anbunathan",
	"jsonValue":"{nathan:dhbh,hdsh:djcbsh,sbdjs:bhsd}",
	"timeToLive":3000
}
]

#get operation of KeyValue accomplised by calling  http://localhost:8866/getJSONObject/{Provide keyname here!!}

#delete operation of KeyValue accomplised by calling  http://localhost:8866/DeleteKey{Provide keyname here!!}


  
