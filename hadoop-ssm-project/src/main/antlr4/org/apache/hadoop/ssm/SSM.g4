grammar SSM;

//parser
prog: directive
;

directive:
         object'.'condition('and'condition)*':'(trigger'|')?command
;

object:
       FILE      #objEQfile
      |DIRECTORY #objEQdir
      |STORAGE   #objEQstorage
      |CACHE     #objEQcache
;

condition:conditioncontent conditionkey conditionvalue
;

conditioncontent:PATH                   #ccEQpath
                |MTIME                  #ccEQmtime
                |ATIME                  #ccEQatime
                |ACCESSCOUNT            #ccEQaccesscount
                |STORAGEPOLICY          #ccEQstoragepolicy
                |ATTRIB                 #ccEQattrib
                |XATTR                  #ccEQxattr
                |LENGTH                 #ccEQlength
                |LASTCACHECOUNT         #ccEQlastcachecount
                |ISINCACHE              #ccEQisincache
                |ISFILEBEINGWRITTEN     #ccEQisfilebeingwritten
                |BLOCKSIZE              #ccEQblocksize
                |QUOTA                  #ccEQquota
                |QUOTAUSED              #ccEQquotaused
                |QUOTAUSEDRATIO         #ccEQquotausedratio
                |NUMFILES               #ccEQnumfiles
                |CAPACITY               #ccEQcapacity
                |USED                   #ccEQused
                |USEDRATIO              #ccEQusedratio
                |FREE                   #ccEQfree
                |USEDINLAST             #ccEQusedinlast
                |USEDRATIOCHANGEINLAST  #ccEQusedratiochangeinlast
;
conditionkey:MATCH          #ckEQmatch
            |GT             #ckEQgt
            |GTOE           #ckEQgtoe
            |LT             #ckEQlt
            |LTOE           #ckEQltoe
            |EQUAL          #ckEQequal
;
conditionvalue:TRUE         #cvEQtrue
              |FALSE        #cvEQfalse
              |PATHTOMATCH  #cvEQpathtomatch
              |INT          #cvEQint
;

trigger:'on' triggervalue
;

triggervalue:FILECREATE     #tvEQfilecreate
            |FILECLOSE      #tvEQfileclose
            |FILEAPPEND     #tvEQfileappend
            |FILERENAME     #tvEQfilerename
            |FILEMETADATA   #tvEQfilemetadata
            |FILEUNLINK     #tvEQfileunlink
            |FILETRUNCATE   #tvEQfiletruncate
;

command:COMMAND #commandval
;

//lexer
//object
FILE:'file'
;
DIRECTORY:'directory'
;
STORAGE:'storage'
;
CACHE:'cache'
;

//condition
//conditioncontent
PATH:'path'
;
MTIME:'mtime'
;
ATIME:'atime'
;
ACCESSCOUNT:'accessCount'
;
STORAGEPOLICY:'storagePolicy'
;
ATTRIB:'attrib'
;
XATTR:'xattr'
;
LENGTH:'length'
;
LASTCACHECOUNT:'lastCacheCount'
;
ISINCACHE:'isInCache'
;
ISFILEBEINGWRITTEN:'isFileBeingWritten'
;
BLOCKSIZE:'blockSize'
;
QUOTA:'quota'
;
QUOTAUSED:'quotaUsed'
;
QUOTAUSEDRATIO:'quotaUsedRatio'
;
NUMFILES:'numFiles'
;
CAPACITY:'capacity'
;
USED:'used'
;
USEDRATIO:'usedRatio'
;
FREE:'free'
;
USEDINLAST:'usedInLast'
;
USEDRATIOCHANGEINLAST:'usedRatioChangeInLast'
;

//condition keyword
MATCH:'match'
;
GT:'>'
;
GTOE:'>='
;
LT:'<'
;
LTOE:'<='
;
EQUAL:'='
;

//condition value
PATHTOMATCH:('/'[a-zA-Z0-9]+)+
;
TRUE:'true'
;
FALSE:'false'
;
INT: [0-9]+
;

//trigger
FILECREATE:'filecreate'
;
FILECLOSE:'fileclose'
;
FILEAPPEND:'fileappend'
;
FILERENAME:'filerename'
;
FILEMETADATA:'filemetadate'
;
FILEUNLINK:'fileunlink'
;
FILETRUNCATE:'filetruncate'
;

//command
COMMAND:[a-z]+
;

//other
WS: [ \t\r\n]+ -> skip
;
STRING:[a-zA-Z]+
;