console.log("boardRegister.js in");

//버튼을 클릭했을때 input 눌렀을때처럼 파일을 선택할 수 있게 해주는 기능
document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('file').click();
});

// 정규표현식을 사용하여 파일의 형식을 제한
// 실행파일 막기 (exe, bat, sh, mis, dll, jar)
// 파일 사이즈 체크(20MB 사이즈보다 크면 막기)
// 이미지 파일만 올리기(jpg, jpeg, gif, png, bmp 외 막기)

const regExp = new RegExp("\.(exe|sh|bat|mis|dll|jar)$"); 
const regExpImg = new RegExp("\.(jpg|jpeg|gif|png|bmp)$");
const maxSize = 1024*1024*20;

// Validation : 규칙설정
// return 0 / 1로 리턴
function fileValidation(name, fileSize){
    let fileName = name.toLowerCase(); //파일 이름을 전부 소문자로 변경
    if(regExp.test(fileName)){ //파일 확장자에 실행 파일 확장자가 있다면... 
        return 0;
    } else if(fileSize > maxSize){ // 파일 사이즈가 max 사이즈보다 크다면...
        return 0;
    } else if(!regExpImg.test(fileName)){ //이미지 파일이 아니면...
        return 0;
    } else{
        return 1;
    }
}

// 첨부파일에 따라 등록이 가능한지 체크 함수
document.addEventListener('change',(e)=>{ 

    console.log(e.target);  // 변화하는 값을 인지
    if(e.target.id === 'file'){
        //여러개의 파일이 배열로 들어오게 됨
        const fileObject = document.getElementById('file').files;
        console.log(fileObject);

        //한 번 true가 된 disabled는 다시 false로 돌아오지 않음
        //한 번 들어오면 버튼을 활성화 시켜줘야 함
        document.getElementById('regBtn').disabled = false;

        let div = document.getElementById('fileZone');
        div.innerHTML = ''; //기존에 등록한 파일이 있다면 지우기..
        let ul = `<ul class="list-group">`;
        // 각각의 파일은 fileValidation에 의해 리턴 여부를 체크
        //모든 vaildation 의 값을 곱해서 넣기 때문에 파일 하나라고 0의 값이 들어오면 false가 됨 
        //모든 파일의 return의 값이 1이어야 가능 
        // *로 isOk를 처리하여 모든 파일이 1인지 체크 
        let isOk = 1;
        for(let file of fileObject){ // 배열 >> for of
            let vaildResult = fileValidation(file.name, file.size); // 한 파일에 대한 결과
            isOk *= vaildResult;
            ul += `<li class="list-group-item">`;
            ul += `<div>${vaildResult?'업로드 가능':'업로드 불가능'}</div> ${file.name} `;
            ul += `<span class="badge text-bg-${vaildResult? 'primary' : 'danger'}"> ${file.size}</span>`;
            ul += `</li>`;
        }
        ul += `</ul>`;
        div.innerHTML = ul;

        // 업로드가 불가능한 파일이 1개라고 있다면, 등록버튼 비활성화
        if(isOk == 0){
            document.getElementById('regBtn').disabled = true; // 비활성화
        }
    }  
})
/*
<ul class="list-group">
  <li class="list-group-item"> <div>업로드 가능</div> 파일 이름
    <span class="badge text-bg-primary">파일 사이즈</span>
  </li>
  <li class="list-group-item"> <div>업로드 불가능</div> 파일 이름
    <span class="badge text-bg-danger">파일 사이즈</span>
  </li>
</ul>
 */

