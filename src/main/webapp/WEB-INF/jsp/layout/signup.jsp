<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/signup.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">

    <script src="<c:url value="/resources/js/bootstrap/popper.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
    <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

    <script type="module" src="<c:url value="/resources/js/signup.js"/>"></script>
</head>

<body>
<form action="/account/register" method="post" class="content" id="register-form" name="signup">
    <div class="pagedesc">
        <h2 class="title">회원 가입</h2>
        <p class="desc">COM's의 회원 가입을 진행합니다.</p>
    </div>
    <div class="noti box border">
        각 서명란 아래에 존재하는 항목에 체크하면 다음으로 진행할 수 있습니다.
    </div>
    <div class="rules box">
        <div class="box-header">
            <h2>동아리 회칙</h2>
        </div>
        <div class="rule-desc box-content">
            <div class="rule-content">
                <h2>제 1 장 개요</h2>
                <h3> 제 1 조</h3>
                <p>본 회는 광운대학교 컴퓨터연구회(이하 동아리)라 한다. 영어로는 Computer Science Research Club. 약자로는
                        COM's로 표기한다.</p>
                    <h3> 제 2 조</h3>
                    <p>본 동아리는 컴퓨터 및 정보통신 분야의 연구와 회원 상호간의 자질 향상을 위한 노력을 통해 지식정보시대를 살아가는 지성인으로부터 능력을
                        갖추고 본 대학 발전에 기여함을 목적으로 한다.</p>
                    <h3>제 3 조</h3>
                    <p>본 회의 기는 별첨도안으로 한다.</p>
                    <h3>제 4 조</h3>
                    <p>본 동아리는 광운대학교 동아리 연합회 산하 학술분과 내에 둔다.</p>
                    <h2>제 3장 회원의 자격, 권리와 의무</h2>
                    <h3>제 5조</h3>
                    <p>본 동아리의 회원은 명예회원, 정회원, 및 준회원으로 구성을 한다.</p>
                    <h3>제 6조 : 회원의 자격</h3>
                    <p>제 1 항 : 본 동아리의 회원이 되고자 하는 자는 본 동아리의 목적에 찬성하고 회칙을 준수해야 한다.</p>
                    <p>제 2 항 : 본 동아리의 준회원, 정회원은 본 대학의 재학생으로 한다.</p>
                    <p>제 3 항 : 본 동아리의 회원은 본 회에서 실시하는 강좌에 참여를 할 수 있다.</p>
                    <p>제 4 항 : 기타 명예회원, 정회원 및 준회원에 대한 자세한 자격은 세칙에 따라 정한다.</p>
                    <h3>제 7 조 : 권리와 의무</h3>
                    <p>제 1 항 : 본 동아리의 모든 회원은 평등권, 발언권을 가진다.</p>
                    <p>제 2 항 : 결의권, 피선거권은 정회원에 한해 정한다.</p>
                    <p>제 3 항 : 본 동아리의 회원은 동아리 활동에 대해 참여하는 권리와 의무를 가진다.</p>
                </div>
                <div class="checkbox">
                    <input type="checkbox" id="club-rule-check" command="rule-check" target="mainSign">
                    <label for="club-rule-check" class="pointer">동아리 회칙에 동의합니다.</label>
                </div>
            </div>
        </div>
        <div class="rules box">
            <div class="box-header">
                <h2>페이지 이용약관</h2>
            </div>
            <div class="rule-desc box-content">
                <div class="rule-content">
                    <h2>제 1장 - 개요</h2>
                    <h3>제 1조 (목적)</h3>
                    <p>광운대학교 중앙동아리 컴퓨터연구회 COM&#x27;S(이하
                        &quot;컴스&quot;)의 홈페이지를 이용해주셔서 감사합니다. 컴스는 동아리 운영의 편의 위해 인터넷 서비스(이하 &quot;홈페이지&quot;)를 운용하고 있으며 본
                        약관은 홈페이지를 이용함에 있어 컴스와 컴스의 회원(이하 &quot;회원&quot;)의 권리, 의무 및 책임사항을 규정함을 목적으로 합니다. </p>
                    <h3>제 2조 (약관의 명시와 설명 및 개정)</h3>
                    <p>① 본 약관의 내용은 홈페이지 하단의 이용약관 페이지에 게시하거나 별도의 방법으로
                        공지하고, 해당 약관에 동의한 모든 회원에게 그 효력이 발생합니다.</p>
                    <p>② 컴스는 필요에 따라 이용약관의 내용을 변경할 수 있습니다. 본 약관을 변경하는
                        경우, 약관 시행일로부터 15일 전까지 홈페이지에 해당 내용을 공지 하며, 변경되는 내용의 경중에 따라 30일 전까지 회원정보에 등록된 이메일 주소로 변경된 약관의 전문을
                        전송 할 수 있습니다.</p>
                    <p>③ 회원이 컴스가 전항에 따라 약관을 통지한날로부터 7일 이내에 별도의 거부의사를
                        표시하지 않는다면 본 약관에 동의한 것으로 간주합니다.</p>
                    <h3>제 3 조 (약관 외 준칙)</h3>
                    <p>본 약관에 규정되지 않은 사항에 대해서는 관련법령 또는 컴스가 정한 개별약관에
                        따릅니다.</p>
                    <h3>제 4조 (용어의 정의)</h3>
                    <ol>
                        <li style="margin-left: 4em;">컴스: 광운대학교 중앙동아리 COM&#x27;S 또는 그 임원단, 홈페이지 관리자 </li>
                        <li style="margin-left: 4em;">홈페이지: 컴스에서 제공하는 웹 서비스</li>
                        <li style="margin-left: 4em;">회원: 홈페이지에 가입한 회원 또는 컴스의 회원</li>
                        <li style="margin-left: 4em;">관리자: 홈페이지 운영 책임자 또는 컴스 임원단</li>
                    </ol>
                    <p></p>
                    <hr />
                    <p></p>
                    <h2>제 2장 - 가입</h2>
                    <h3>제 5조 (회원가입)</h3>
                    <p>① 컴스 가입신청은 본 홈페이지의 회원가입 페이지에서 컴스가 요구하는 정보를
                        입력하는 방식으로 이루어집니다.</p>
                    <p>② 컴스의 회원가입은 여러분이 본 약관에 동의한 뒤 1항에서 정한 방식으로
                        가입신청을 하면 컴스는 가입신청 내용을 검토하여 가입을 승낙함으로써 이루어집니다.</p>
                    <h3>제 6조 (가입의 제한)</h3>
                    <p>① 동아리는 가입신청자가 아래 각 호에 해당하는 경우에는 가입 승낙을 유보하거나
                        거부할 수 있고, 아래 조건을 위반하여 가입한 사실이 드러난 경우 회원자격 및 계정이용을 제한할 수 있습니다.</p>
                    <ol>
                        <li>본 홈페이지 이용약관을 위반하여 회원자격을 제한당하거나 계정이 정지 또는 삭제된 경우</li>
                        <li>타인의 개인정보를 이용하여 가입신청을 하려 한 경우</li>
                        <li>가입신청에 필수적인 정보를 입력하지 않거나 허위로 작성한 경우</li>
                        <li>본 회의 기수 인원이 현격히 많은경우</li>
                        <li>서비스 제공을 위한 기술적 문제가 있는 경우</li>
                        <li>기타 법령에 위배되거나 컴스 내부의 기준에 반하는 경우</li>
                    </ol>
                    <p></p>
                    <hr />
                    <p></p>
                    <h2>제 3장 - 이용</h2>
                    <h3>제 7조 (서비스 제공 및 변경)</h3>
                    <p>① 컴스가 홈페이지를 통해 제공하는 서비스(이하 &quot;서비스&quot;)는
                        아래와 같습니다</p>
                    <ol>
                        <li>홈페이지 내의 컨텐츠 이용 및 작성 서비스</li>
                        <li>동아리 일정 및 활동 공지 서비스</li>
                        <li>기타 컴스에서 제공하는 일체의 서비스</li>
                    </ol>
                    <p>② 제공하는 서비스에 변동이 생기거나 새로운 서비스를 제공하는 경우 12조의 내용에
                        따라 그 내용을 공지합니다.</p>
                    <p></p>
                    <h3>제 8조 (서비스의 중단)</h3>
                    <p>① 컴스는 서비스 제공이 필요한 시스템의 점검, 교체 및 고장 등의 사유가 발생한
                        경우 서비스의 제공을 일시적으로 중단할 수 있습니다.</p>
                    <p>② 1항에 의해 서비스가 중단된 경우 가능한 수단을 통해 회원에게 그 사실을 즉시
                        공지하도록 합니다. 단, 예측 및 통제가 불가능한 서비스의 중단의 경우에는 그러지 아니합니다.</p>
                    <p></p>
                    <h3>제 9조 (계정의 관리)</h3>
                    <p>① 계정은 계정 소유자 본인만 이용할 수 있으며, 타인에게 양도 및 판매할 수
                        없습니다. 타인이 무단으로 계정을 사용하지 않도록 비밀번호 관리에 유의하시기 바랍니다.</p>
                    <p>② 회원은 본인의 계정의 개인정보를 수정 및 조회할 수 있습니다. 단, 정확한 회원
                        식별을 위해 변경 불가능할 수 있으며, 해당 정보의 변경을 원하는 경우 컴스 또는 홈페이지 관리자에게 알려주시기 바랍니다.</p>
                    <p>③ 컴스는 동아리 활동 및 홈페이지 이용에 중요한 공지를 전달하기위해 계정정보를
                        활용합니다. 회원은 공지의 정확한 전달을 위해 계정정보를 적시에 수정해야하며, 잘못된 계정정보 입력으로 인해 발생한 문제에 대해 컴스는 책임을 부담하지 않습니다.</p>
                    <p></p>
                    <hr />
                    <p></p>
                    <h2>제 4장 - 의무</h2>
                    <h3>제 10조 (회원의 의무)</h3>
                    <p>① 회원은 홈페이지를 이용할 때 아래의 행위를 해서는 안 됩니다.</p>
                    <ol>
                        <li>가입신청 시 허위 사실을 기재하거나, 다른 회원의 계정 및 개인정보를 무단으로 이용하는 행위</li>
                        <li>타인을 비난하거나 명예를 훼손하는 행위</li>
                        <li>컴스 혹은 타인의 저작권 및 권리를 침해하는 행위</li>
                        <li>공공질서 및 미풍양속에 위반되는 내용의 정보, 문장, 도형, 음성 등을 타인에게 유포하거나 음란물 등을 게시하는 행위</li>
                        <li>홈페이지 서비스의 제공을 고의로 방해하는 일체의 행위</li>
                        <li>컴스가 제공하는 서비스 및 소프트웨어(코드)를 해당하는 라이센스를 위반하여 이용하거나 무단으로 분해, 모방, 역설계하는 행위</li>
                        <li>타인의 개인정보를 수집, 저장, 공개하는 행위</li>
                        <li>임원단 또는 관리자를 사칭하는 행위</li>
                        <li>기타 불법한 행위</li>
                    </ol>
                    <p></p>
                    <p>② 회원은 자신의 계정 및 지위를 타인에게 증여, 양도 및 판매해서는 안 됩니다.
                    </p>
                    <p>③ 컴스의 약관 및 관련 법령을 위반한 경우, 컴스는 위반사항에 대해 조사할 수
                        있으며 결과에 따라 홈페이지 이용에 제한이 가해지거나 관련 법령을 통해 처벌받을 수 있습니다.</p>
                    <p></p>
                    <h3>제 11조 (컴스의 의무)</h3>
                    <p>회원이 제공한 모든 개인정보의 안전한 보관 및 처리는 컴스의 가장 중요한
                        의무입니다. 회원의 동의 없이는 컴스와 회원 이외의 제 3자에게 어떠한 개인정보도 제공하지 않습니다. 자세한 정보는 개인정보 처리방침을 참조하시기 바랍니다. </p>
                    <p></p>
                    <h3>제 12조 (회원에 대한 공지)</h3>
                    <p>회원의 동아리 활동 및 홈페이지 이용에 중요한 공지는 7일이상 공지게시판을 통해
                        공지하여야 하며, 중대한 공지의 경우 계정정보에 등록된 이메일 주소로 이메일을 할송하거나 휴대전화번호로 카카오톡 메시지 또는 SMS를 발송하는 방식으로 공지해야합니다. 그
                        내용에 이견이 있거나 거부하는 경우 컴스에 그 의견을 전달해 주시기 바랍니다.</p>
                    <p></p>
                    <hr />
                    <p></p>
                    <h2>제 5장 - 탈퇴</h2>
                    <h3>제 13조 (회원탈퇴)</h3>
                    <p>① 회원이 동아리 활동 및 서비스 이용을 더 이상 원치 않는 경우에는 회원탈퇴를 할
                        수 있습니다. 컴스는 회원탈퇴 신청하는 즉시 해당 회원을 회원탈퇴 처리합니다.</p>
                    <p>② 컴스는 관련 법령에 따라 일정기간 홈페이지 이용이 없는 회원에 대해 일부
                        개인정보를 파기 혹은 분리보관할 수 있습니다. 자세한 내용은 개인정보 처리방침을 참조하시기 바랍니다.</p>
                    <p>③ 회원탈퇴 이후 관련 법령에 따라 개인정보를 삭제합니다. 다만, 작성한 게시물 및
                        댓글 등은 삭제되지 않을 수 있습니다.</p>
                    <p>
                    </p>
                    <p>
                </div>
                <div class="checkbox">
                    <input type="checkbox" id="site-rule-check" command="rule-check" target="mainSign">
                    <label for="site-rule-check" class="pointer"> 홈페이지 이용 약관에 동의합니다.</label>
                </div>
            </div>
        </div>
        <div class="rules box">
            <div class="box-header">
                <h2>개인정보 수집·제공 동의</h2>
            </div>
            <div class="rule-desc box-content">
                <div class="rule-content">
                    <p>COM&#x27;S(이하 &quot;컴스&quot;)는 정보통신망법 규정에 따라 컴스에
                        가입신청하는 모든 분들께 수집하는 개인정보의 항목, 수집 및 이용목적, 보유 및 이용기간에 대해 안내해 드립니다. 컴스에 가입하고자 하시는 신청자께서는 아래 항목을 읽은뒤에
                        동의해 주시기
                        바랍니다.</p>
                    <p></p>
                    <h3>1. 수집하는 개인정보</h3>
                    <p>컴스는 동아리 운영과 회원관리의 편의를 위해 아래와 같은 개인정보를 수집합니다.</p>
                    <p><b>회원가입 시점에 컴스가 신청인으로부터 수집하는 개인정보</b></p>
                    <ul>
                        <li>&#x27;아이디, 비밀번호, 이름, 전공, 재학상태, 생년월일, 동아리 기수, 휴대전화번호, 이메일주소&#x27;를 필수항목으로 수집합니다.</li>
                        <li>&#x27;자택 주소&#x27;를 선택항목으로 수집합니다.</li>
                    </ul>
                    <p></p>
                    <p><b>서비스 이용과정에서 수집하는 개인정보</b></p>
                    <p>컴스는 서비스 개선 및 관련 법령의 준수를 위해 IP주소, 서비스 이용기록 등을 자동화된 방법으로
                        생성 및 저장할 수 있습니다.</p>
                    <p></p>
                    <p>수집된 개인정보는 내부 데이터베이스 및 종이문서에 기록되며, 컴스는 자체적으로 개인정보 안전조치
                        기준을 수립하여 개인정보 보호 및 접근 통제에 최선을 다하고 있습니다.</p>
                    <p></p><br>
                    <h3>2. 수집한 개인정보의 이용</h3>
                    <p>컴스는 아래 기술된 동아리 활동 및 서비스 개선 목적으로만 수집한 개인정보를 이용합니다.</p>
                    <ul>
                        <li>회원가입 의사의 확인 및 동아리 회원 식별 등의 회원관리 목적</li>
                        <li>동아리 회원간의 친목 도모 및 행사안내를 위한 카카오톡 채팅방 개설 목적</li>
                        <li>기타 동아리 활동에 필요하다고 판단되는 경우</li>
                    </ul>
                    <p></p><br>
                    <h3>3. 개인정보의 파기</h3>
                    <p><b>컴스는 회원 탈퇴시 해당 회원의 개인정보를 지체없이 파기하고
                            있습니다.</b></p>
                    <p>단, 법령에서 정보 보관의 의무를 부과하거나 별도의 동의를 얻은 항목의 경우 일정 기간동안
                        개인정보를 보관합니다.</p>
                    <p></p>
                    <p>1년이상 서비스 이용 기록이 없는 회원의 개인정보는 일반 회원의 개인정보와 별도로 분리보관하고
                        있음을 알려드립니다.</p>
                </div>
                <div class="checkbox">
                    <input type="checkbox" id="info-rule-check" command="rule-check" target="mainSign">
                    <label for="info-rule-check" class="pointer"> 개인정보 수집 및 제공에 동의합니다.</label>
                </div>
            </div>
        </div>

        <div class="mainSign box">
            <div class="box-header">
                <h2>정보입력</h2>
            </div>
            <div class="box-content">
                <div class="s-item">
                    <div class="s-name">
                        <p>아이디</p>
                        <p class="desc">
                            <span req></span>
                            <span immutable></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <div class="id-field">
                            <input type="text" name="alias" id="sign-id" class="near right" placeholder="ID" required>
                            <button type='button' id="id-checker" class="near left gray">아이디확인</button>
                        </div>
                            <p class="desc" id="id-status"></p>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>비밀번호</p>
                        <p class="desc">
                            <span req></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <input type="password" name="password" id="sign-pw1" placeholder="비밀번호" autocomplete="off"
                            bind="sign-pw2" desc="pw-checker" required>
                        <p class="desc" id="pw-checker"></p>
                        <hr class="noshow">
                        <input type="password" name="sign-pw-again" id="sign-pw2" placeholder="비밀번호 확인" autocomplete="off"
                            desc="pw-rechecker" required>
                        <p class="desc" id="pw-rechecker"></p>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>이름</p>
                        <p class="desc">
                            <span req></span>
                            <span immutable></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <input type="text" name="name" id="sign-name" placeholder="이름" desc="name-status" required>
                        <p class="desc" id="name-status"></p>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>전공</p>
                        <p class="desc">
                            <span req></span>
                            <span immutable></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <select name="major" id="major" class="gray" required>
                            <option value="" selected hidden>선택</option>
                            <c:forEach var="colleges" items="${collegeMatrix}">
                                <optgroup label="${colleges[0].college}">
                                    <c:forEach var="majors" items="${colleges}">
                                        <option value="${majors.major_idx}"><c:out value="${majors.major}" /></option>
                                    </c:forEach>
                                </optgroup>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>재학상태</p>
                        <p class="desc">
                            <span req></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <input type="hidden" name="status" id="status">
                        <select name="statusText" class="gray" required>
                            <option value="" selected hidden>선택</option>
                        </select>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>생년월일</p>
                        <p class="desc">
                            <span req></span>
                            <span immutable></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <input type="date" name="birth" id="birth" max="2099-12-31" required>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>기수</p>
                        <p class="desc">
                            <span req></span>
                            <span immutable></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <input type="number" name="term" id="term" min="1" required>
                        <hr class="noshow">
                        <p class="desc" id="termMax"><br></p>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>휴대전화</p>
                        <p class="desc">
                            <span req></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <input type="text" name="phone_num" id="phone_num" maxlength="13" placeholder="전화번호" required>
                        <hr class="noshow">
                        <div class="checkbox">
                            <input type="checkbox" name="sms_send" id="sign-phone-msg" checked="false">
                            <label for="sign-phone-msg" class="pointer">메시지 수신 동의</label>
                        </div>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>이메일</p>
                        <p class="desc">
                            <span req></span>
                        </p>
                    </div>
                    <div class="s-value">
                        <input type="hidden" name="email_addr" id="email_addr">
                        <input type="text" id="email_addr-id" required> <span>@</span>
                        <div class="domain-field">
                            <input type="text" id="email_addr-domain" class="near right" value="" required>
                            <select class="email-domain near left gray">
                            </select>
                        </div>
                        <div class="checkbox">
                            <input type="checkbox" name="email_send" id="sign-email-msg" checked="false">
                            <label for="sign-email-msg" class="pointer">이메일 수신 동의</label>
                        </div>
                    </div>
                </div>
                <div class="s-item">
                    <div class="s-name">
                        <p>자택주소</p>
                    </div>
                    <div class="s-value">
                        <input type="hidden" name="home_addr" id="home_addr">
                        <div class="post-num-field">
                            <input type="text" id="sign-post-num" class="near right" placeholder="우편 번호">
                            <input type="button" id="btn-address" class="near left gray" value="우편번호 찾기"><br>
                        </div>
                        <hr class="noshow">
                        <input type="text" id="sign-address1" placeholder="기본주소"><br>
                        <hr class="noshow">
                        <input type="text" id="sign-address2" placeholder="상세주소">
                        <input type="text" id="sign-address3" placeholder="참고항목">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <input type="button" id="homeBtn" class="dark" value="홈으로 돌아가기">
                <input type="submit" id="submit" class="dark" value="회원가입" class="mainSign">
            </div>
        </div>
    </form>
</body>