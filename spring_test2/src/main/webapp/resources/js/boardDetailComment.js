console.log("boardDetailComment.js in")
console.log(bnoVal);

// cmtAddBtn 버튼을 클릭하면 bno, writer, content를 비동기로 DB에 넣기
document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText');
    const cmtWriter = document.getElementById('cmtWriter').innerText;

    if(cmtText.value == null || cmtText.value == ''){
        alert('댓글입력필요');
        cmtText.focus();
        return false;
    } else{
        let cmtData={
            bno:bnoVal,
            writer:cmtWriter,
            content:cmtText.value
        };
        postCommentToServer(cmtData).then(result=>{
            if(result == 1){
                console.log(">>> "+result);
                alert('댓글 등록 완료');
                cmtText.value ='';
                // 화면 뿌리기
                spreadCommentList(bnoVal);
            }
        })
    }
})

async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config={
            method: 'post',
            headers:{
                'Content-type':'application/json; charset=UTF-8'
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

async function getCommentListFromServer(bno, page){
    try {
        const resp = await fetch("/comment/"+bno+"/"+page);
        const result = await resp.json(); //리스트로 가져오기 때문에 json()형태로 넣어야 함
        return result;
    } catch (error) {
        console.log(error);
    }
}

// 옵셔널 파라미터 page=1 값이 없으면 기본 옵션으로 1을 세팅
function spreadCommentList(bno, page=1){
    getCommentListFromServer(bno, page).then(result=>{
        console.log(result); //ph값을 가지고 리턴됨 
        const ul = document.getElementById('cmtListArea');
        if (result.cmtList.length > 0) {
            if(page == 1){ // 더보기 눌렀을때 누적되어 보여지기 위해
                ul.innerHTML = '';
            }
            for (let cvo of result.cmtList){
                let li = `<li class="list-group-item" data-cno="${cvo.cno}">`;
                    li += `<div class="input-group mb-3"> no. ${cvo.cno} / `;
                    li += `<div class="fw-bold"> ${cvo.writer} </div>`;
                    li += `${cvo.content}</div>`;
                    li += `<span class="badge rounded-pill text-bg-warning">${cvo.regdate} </span>`;
                    // 수정, 삭제 버튼
                    li += `<button type="button" class="btn btn-outline-warning btn-sm mod" data-bs-toggle="modal" data-bs-target="#myModal"> % </button>`;
                    li += `<button type="button" class="btn btn-outline-danger btn-sm del" data-cno="${cvo.cno}"> X </button>`;
                    li += `</li>`;
                    ul.innerHTML += li;
            }
            // 더보기 버튼 코드 작업
            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);
            // moreBtn 표시되는 조건
            // 해당하는 페이지의 댓글 값이 5개 이상이여야 함 / 다 표현되고 나면 없어져야 함
            // pgvo.pageNo = 1 / realEndPage = 4
            // realEndPage > 현재 내 페이지가 작으면 표시 
            if(result.pgvo.pageNo < result.realEndPage){
                // style="visibility:hidden" : 숨김 / style="visibility:"visible" : 표시
                moreBtn.style.visibility = 'visible'; // 버튼 표시
                moreBtn.dataset.page = page+1; // 한 페이지 늘리는 작업
            }else{
                moreBtn.style.visibility = 'hidden'; // 5개 이상의 댓글이 없다면 그대로 숨김 처리 
            }

        }else{
            ul.innerHTML =`<div class="input-group mb-3"> Comment List Empty </div>`;
        }
    })
}

document.addEventListener('click',(e)=>{
    console.log(e.target);
    if(e.target.id == 'moreBtn'){ // moreBtn을 눌렀을 때 화면에 한 번 더 뿌려주는 작업
        let page = parseInt(e.target.dataset.page) ;
        spreadCommentList(bnoVal, page);
    }
    else if(e.target.classList.contains('mod')){
        // 내가 수정버튼을 누른 댓글의 li를 가져오기
        let li = e.target.closest('li');
        // nextSibling : 한 부모 안에서 다음 형제를 찾는 명령어
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;

        // 수정 => cno dataset으로 달기 cno, content 필요
        document.getElementById('cmtModBtn').setAttribute('data-cno', li.dataset.cno);
    } 
    else if (e.target.id == 'cmtModBtn'){
        let cmtModData ={
            cno:e.target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        };
        console.log(cmtModData);

        // 비동기로 전달
        modifyCommentToServer(cmtModData).then(result=>{
            if(result == '1'){
                alert('댓글 수정 완료');
                document.querySelector('.btn-close').click();
            }else {
                alert('댓글 수정 실패');
                document.querySelector('.btn-close').click();
            }
            // 새로 뿌리기
            spreadCommentList(bnoVal);
        });
    }
    else if (e.target.classList.contains('del')){
        let cnoVal = e.target.dataset.cno;
        // let li = e.target.classList('li');
        // let cnoVal = li.dataset.cno;

        removeCommentFromServer(cnoVal).then(result=>{
            if(result == '1'){
                alert('댓글 삭제 완료');
                spreadCommentList(bnoVal);
            }
        })
    }

})

// 수정 시 모달창을 통해 댓글 입력 받기
async function modifyCommentToServer(cmtModData){
    try {
        const url = "/comment/modify";
        const config ={
            method : 'put',
            headers : {
                'Content-Type':'application/json; charset=UTF-8'
            },
            body:JSON.stringify(cmtModData)
        };
        
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function removeCommentFromServer(cno){
    const url = "/comment/"+cno;
    const config = {
        method : "delete"
    };
    
    const resp = await fetch (url, config);
    const result = await resp.text();
    return result;
}