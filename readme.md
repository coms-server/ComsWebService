<p align="center">
  <a href="https://github.com/SnowlyFeast/Coms-Server">
    <img src="src/main/resources/static/css/ico/logo.ico" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">COM's</h3>

  <p align="center">
    광운대학교 컴퓨터연구회(COM's) 웹페이지 개발 프로젝트입니다.
    <br />
    <a href="https://coms.kw.ac.kr"><strong> 페이지로 이동 »</strong></a>
  </p>
</p>

## 개발 및 배포 환경
* Java 11 (Spring Boot 2.4.1)
* Jquery 3.4.1 & BootStrap 4.3.1(일부)
* Tomcat 8
* Maria DB 10
* Ubuntu Server 18

## 도움을 주신 분들...
<a href="https://github.com/SnowlyFeast"><img src="https://avatars0.githubusercontent.com/u/33473622?s=460&v=4" title="SnowlyFeast" width="80" height="80"></a>
<a href="https://github.com/grayroom"><img src="https://avatars0.githubusercontent.com/u/43588644?s=460&u=fd358afa0e9b262bc52498cb20616081b1ee1a8a&v=4" title="grayroom" width="80" height="80"></a>
<a href="https://github.com/mijien0179"><img src="https://avatars0.githubusercontent.com/u/40613626?s=460&u=35261c790e2a070c1b01ed1020fc4ea906794c73&v=4" title="mijien0179" width="80" height="80"></a>
<a href="https://https://github.com/RapidWorkers"><img src="https://avatars2.githubusercontent.com/u/6492071?s=400&u=d38d43f09a55ecddb7453414707a578f4e755e4c&v=4" title="RapidWorkers" width="80" height="80"></a>

## SCSS
이 프로젝트에서는 css를 직접 작성하는 대신 scss로 작성된 스타일시트를 dart-sass로 컴파일하여 사용합니다. Node.js환경에서 npm을 사용한다는 가정하에 아래 명령어를 통해 sass(scss)를 설치할 수 있습니다.
```
npm install -g sass
```

특정 scss파일을 변환하고자 하는경우, 아래와같이 입력하면 해당 scss 파일이 변경될 때 마다 동일한 css파일이 생성/업데이트 됩니다.
```
sass --watch <변환할 scss파일 경로>
```

폴더단위로 변환하고자 하는경우, 아래와같이 입력합니다.
```
sass --watch <변환할 폴더 경로>:<출력될 css파일의 경로(선택적)>
```

더 자세한 정보는 링크를 참조하세요
https://github.com/sass/dart-sass

## 라이선스 준수
이 프로젝트에서 사용된 오픈소스는 아래와 같습니다.
* `jquery` https://openjsf.org/
* `quill.js` https://quilljs.com/
* `moment.js` https://momentjs.com/
* `Event Calendar Widget(CodePen)` https://codepen.io/peanav/pen/ulkof

또한, 이 프로젝트에는 네이버에서 제공한 나눔글꼴이 적용되어 있습니다.
<details>
<summary>나눔글꼴 라이센스 전문</summary>
Copyright (c) 2010, NAVER Corporation (https://www.navercorp.com/),



with Reserved Font Name Nanum, Naver Nanum, NanumGothic, Naver NanumGothic, NanumMyeongjo, Naver NanumMyeongjo, NanumBrush, Naver NanumBrush, NanumPen, Naver NanumPen, Naver NanumGothicEco, NanumGothicEco, Naver NanumMyeongjoEco, NanumMyeongjoEco, Naver NanumGothicLight, NanumGothicLight, NanumBarunGothic, Naver NanumBarunGothic, NanumSquareRound, NanumBarunPen



This Font Software is licensed under the SIL Open Font License, Version 1.1.
This license is copied below, and is also available with a FAQ at: http://scripts.sil.org/OFL



SIL OPEN FONT LICENSE
Version 1.1 - 26 February 2007 


[DEFINITIONS]
"Font Software" refers to the set of files released by the Copyright Holder(s) under this license and clearly marked as such. This may include source files, build scripts and documentation.  

 

"Reserved Font Name" refers to any names specified as such after the copyright statement(s).  

"Original Version" refers to the collection of Font Software components as distributed by the Copyright Holder(s).  

 

"Modified Version" refers to any derivative made by adding to, deleting, or substituting in part or in whole any of the components of the Original Version, by changing formats or by porting the Font Software to a new environment.‘  

 

"Author" refers to any designer, engineer, programmer, technical writer or other person who contributed to the Font Software.  

[PREAMBLE]
The goals of the Open Font License (OFL) are to stimulate worldwide development of collaborative font projects,

to support the font creation efforts of academic and linguistic communities, and to provide a free and open framework  in which fonts may be shared and improved in partnership with others.

The OFL allows the licensed fonts to be used, studied, modified and redistributed freely as long as they are not sold 

by themselves. The fonts, including any derivative works, can be bundled, embedded, redistributed and/or sold with any software provided that any reserved names are not used by derivative works. 

The fonts and derivatives, however, cannot be released under any other type of license.

The requirement for fonts to remain under this license does not apply to any document created using the fonts or their derivatives.  

[PERMISSION & CONDITIONS]

Permission is hereby granted, free of charge, to any person obtaining a copy of the Font Software, to use, study, copy, merge, embed, modify, redistribute, and sell modified and unmodified copies of the Font Software, subject to the following conditions:  
1) Neither the Font Software nor any of its individual components,in Original or Modified Versions, may be sold by itself.
2) Original or Modified Versions of the Font Software may be bundled, redistributed and/or sold with any software, provided that each copy contains the above copyright notice and this license. These can be included either as stand-alone text files, human-readable headers or in the appropriate machine-readable metadata fields within text or binary files as long as those fields can be easily viewed by the user.  
3) No Modified Version of the Font Software may use the Reserved Font Name(s) unless explicit written permission is granted by the corresponding Copyright Holder. This restriction only applies to the primary font name as presented to the users.  
4) The name(s) of the Copyright Holder(s) or the Author(s) of the Font Software shall not be used to promote, endorse or advertise any Modified Version, except to acknowledge the contribution(s) of the Copyright Holder(s) and the Author(s) or with their explicit written permission.  
5) The Font Software, modified or unmodified, in part or in whole, must be distributed entirely under this license, and must not be distributed any other license. The requirement for fonts to remain under this license does not apply to any document created using the Font Software.

[TERMINATION]

This license becomes null and void if any of the above conditions are not met. 


[DISCLAIMER]

THE FONT SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF COPYRIGHT, PATENT, TRADEMARK, OR OTHER RIGHT. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, INCLUDING ANY GENERAL, SPECIAL, INDIRECT, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF THE USE OR INABILITY TO USE THE FONT SOFTWARE OR FROM OTHER DEALINGS IN THE FONT SOFTWARE.  
</details>
