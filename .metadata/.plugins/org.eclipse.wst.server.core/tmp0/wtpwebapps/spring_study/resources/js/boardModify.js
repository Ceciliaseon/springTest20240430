console.log("boardModify.js in");

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('file-x')){
        let uuid=e.target.dataset.uuid;
        let bno=e.target.dataset.bno;
        console.log(uuid);
        removeFileToServer(uuid, bno).then(result=>{
            alert('파일 삭제 완료');
            e.target.closest('li').remove();
        })
    }
})

async function removeFileToServer(uuid, bno){
    try {
        const url = '/board/'+uuid+'/'+bno;
        const config = {
            method:'delete'
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;

    } catch (error) {
        console.log(error);
    }
}

