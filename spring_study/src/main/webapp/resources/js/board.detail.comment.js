console.log("test in");
console.log(bnoVal);




//cmtData만드는 과정 
document.getElementById('cmtAddBtn').addEventListener('click',()=>{ // 2 > 이후 controller
    const cmtText = document.getElementById('cmtText').value;
    const cmtWriter = document.getElementById('cmtWriter').innerText;

    if(cmtText == null || cmtText == ''){
        alert('댓글을 입력해주세요');
        document.getElementById('cmtText').focus();
        return false;
    }else{
        // 댓글 등록
        let cmtData={
            bno:bnoVal,
            writer: cmtWriter,
            content: cmtText
        }
        console.log(cmtData);
        postCommentToServer(cmtData).then(result =>{
            console.log(result);
            if(result == '1'){
                alert('댓글 등록 성공');
                document.getElementById('cmtText').value ='';
                // 화면에 뿌리기
                spreadCommentList(bnoVal);
            }
        })
    }

})

// 비동기 통신 restful
// post : 데이터 객체를 컨트롤러로 보낼 때 (삽입)
// get : 조회 (생략가능)
// put(petch) : 데이터 수정
// delete : 데이터 삭제 


async function postCommentToServer(cmtData){ // 1
    try {
        const url = "/comment/post";
        const config = {
            method: 'post',
            headers:{
                'Content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text(); //body만 텍스트로 때온다는 의미 리턴값 >> isOk
        return result;

    } catch (error) {
        console.log(error);
    }
}

// 5 댓글 뿌리기 메서드
function spreadCommentList(bno){
    getCommentListFromServer(bno).then(result =>{
        console.log(result);
        // 실제 뿌려기는 구문  6
        const div = document.getElementById('accordionExample');
        if(result.length > 0){
            // 반복
            div.innerHTML = ""; // 반복하기 전 기존 샘플 버리기
            for (let i=0;i<result.length;i++){
                let add = `<div class="accordion-item">`;
                    add += `<h2 class="accordion-header">`;
                    add += `<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`;
                    add += `no. ${result[i].cno} / ${result[i].writer} / [${result[i].regdate}] </button> </h2>`;
                    add += `<div id="collapse${i}" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">`;
                    add += `<div class="accordion-body">`;
                    if(id === result[i].writer){
                        add += `<button type="button" data-cno="${result[i].cno}" class="btn btn-outline-warning btn-sm cmtModBtn">수정</button>`;
                        add += `<button type="button" data-cno="${result[i].cno}" class="btn btn-outline-danger btn-sm cmtDelBtn">삭제</button>`;
                    }
                    add += `<input type="text" class="form-control cmtText" value="${result[i].content}">`;
                    add += `</div></div></div>`;
                    
                    div.innerHTML += add;
            }
        } else{
            // 댓글 값이 없을 때 보여지는 구문 
            div.innerHTML = `<div class="accordion-body"> Comment List Empty </div>`;

        }
    })
}

//9
document.addEventListener('click',(e)=>{
    console.log(e.target);
    if(e.target.classList.contains('cmtModBtn')){
        let cnoVal = e.target.dataset.cno;

        //closest 가장 가까이 있는 
        let div = e.target.closest('div');
        let cmtText = div.querySelector('.cmtText').value;
        let cmtData ={
            cno: cnoVal,
            content: cmtText
        };

        // update 비동기 호출
        updateCommentFromServer(cmtData).then(result=>{
            if(result == '1'){
                alert('댓글 수정완료');
                spreadCommentList(bnoVal);
            }
        })
    }

    if (e.target.classList.contains('cmtDelBtn')){
        let cnoVal = e.target.dataset.cno;
        removeCOmmentFromServer(cnoVal).then(result =>{
            if(result == '1'){
                alert('댓글삭제 완료');
                spreadCommentList(bnoVal);
            }
        })
    }
})

// 4
async function getCommentListFromServer(bno){
    try {
        //resful 에서는 ?를 사용하지 않음
        // /comment/306 >> 이와 같이 값이 들어감 
        const resp = await fetch("/comment/"+bno);
        const result = await resp.json();
        return result;

    } catch (error) {
        console.log(error);
    }
}

//7
async function updateCommentFromServer(cmtData){
    try {
        const url = '/comment/modify'
        const config={
            method:'put',
            headers:{
                'Content-Type':'application/json; charset=utf-8'
            },
            body:JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;

    } catch (error) {
        console.log(error);
    }
}

//8
async function removeCOmmentFromServer(cno){
    try { //restful로 데이터를 지울 때는 method 꼭 작성할 것
        const url = '/comment/'+cno;
        const config={
            method:'delete'
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;

    } catch (error) {
        console.log(error)
;    }
}
