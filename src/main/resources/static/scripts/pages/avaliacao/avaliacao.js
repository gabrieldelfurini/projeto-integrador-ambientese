const questionNumbers = 10;
let formProps;
let allQuestions = [];

const addFile = () => {
    alert('W.I.P');
}

const renderQuestions = (questions, isLast) => {
    if (questions.length === 0) return;
    const otherCategory = document.querySelectorAll('.questions-category');
    const questionsCategory = document.createElement('div');
    if (otherCategory.length > 0) {
        questionsCategory.style.display = 'none';
    }
    questionsCategory.classList.add('questions-category');
    questionsCategory.innerHTML = `<h2>${!formProps.isNew ? questions[0].eixo : questions[0].perguntaEixo}</h2>`;
    questions.forEach(question => {
        if (!formProps.isNew) {
            question = {
                id: question.perguntaId,
                descricao: question.perguntaDescricao,
                eixo: question.perguntaEixo,
                resposta: question.respostaUsuario
            }
        }
        const div = document.createElement('div');
        const FwhatsChecked = () => {
            const randomNumber = Math.random();
    
            if (randomNumber < 0.4) {
                return 1; // Retorna 1 com 40% de probabilidade
            } else if (randomNumber < 0.8) {
                return -1; // Retorna -1 com 40% de probabilidade
            } else {
                return 0; // Retorna 0 com 20% de probabilidade
            }
        }
        const whatsChecked = FwhatsChecked();
        div.classList.add('form-question');
        div.innerHTML = `
            <h2>${question.perguntaDescricao}</h2>
            <div class="form-answer">
                <label>
                    <input type="radio" name="answer-${question.perguntaId}" value="Conforme" ${question.resposta ? question.resposta === "Conforme" ? 'checked' : null : (enviornment === 'dev' && whatsChecked === 1 ? 'checked' : null)}>
                    <p>Conforme</p>
                </label>
                <br>
                <label>
                    <input type="radio" name="answer-${question.perguntaId}" value="NaoConforme" ${question.resposta ? question.resposta === "NaoConforme" ? 'checked' : null : (enviornment === 'dev' && whatsChecked === -1 ? 'checked' : null)}>
                    <p>Não conforme</p>
                </label>
                <br>
                <label>
                    <input type="radio" name="answer-${question.perguntaId}" value="NaoSeAdequa" ${question.resposta ? question.resposta === "NaoSeAdequa" ? 'checked' : null : (enviornment === 'dev' && whatsChecked === 0 ? 'checked' : null)}>
                    <p>Não aplicavel</p>
                </label>
                <!-- <div class="add-file">
                    <button class="btn-file" onClick="addFile()">Adicionar Arquivo</button>
                </div> -->
            </div>
        `;
        return questionsCategory.appendChild(div);
    });

    const form = document.querySelector('.form-avaliacao');
    const divBtns = document.createElement('div');
    divBtns.classList.add('btns');
    const btnSubmit = document.createElement('button');
    btnSubmit.id = isLast ? 'btn-submit' : 'btn-next';
    btnSubmit.classList.add(isLast ? 'btn-submit' : 'btn-next');
    btnSubmit.textContent = isLast ? 'Finalizar' : 'Próximo';
    btnSubmit.addEventListener('click', () => {
        if (isLast) {
            let isComplete = true;
            if (!isAllQuestionsAnswered(allQuestions)) {
                confirmationModal({
                    title: 'Atenção',
                    message: 'Você não respondeu todas as perguntas, deseja finalizar mesmo assim?',
                    confirmText: 'Finalizar avaliação',
                    cancelText: 'Cancelar',
                    onCancel: () => {
                        return;
                    },
                    onConfirm: () => {
                        isComplete = false;
                        sendQuestions(isComplete, true);
                    }
                });
            } else {
                sendQuestions(isComplete);
            }
        } else {
            sendQuestions(false);
            toastAlert('Respostas salvas com sucesso', 'success');
            const nextCategory = questionsCategory.nextElementSibling;
            questionsCategory.style.display = 'none';
            nextCategory.style.display = 'flex';
            form.scrollTop = 0;
        }
    });
    if (otherCategory.length > 0) {
        const btnPrev = document.createElement('button');
        btnPrev.id = 'btn-prev';
        btnPrev.classList.add('btn-prev');
        btnPrev.textContent = 'Voltar';
        btnPrev.addEventListener('click', () => {
            const prevCategory = questionsCategory.previousElementSibling;
            questionsCategory.style.display = 'none';
            prevCategory.style.display = 'flex';
            form.scrollTop = 0;
        });
        divBtns.appendChild(btnPrev);
    }

    divBtns.appendChild(btnSubmit);
    questionsCategory.appendChild(divBtns);
    form.appendChild(questionsCategory);
}

const isAllQuestionsAnswered = (allQuestions) => {
    for (let i = 0; i < allQuestions.length; i++) {
        const answer = document.querySelector(`input[name="answer-${!formProps.isNew ? allQuestions[i].id : allQuestions[i].perguntaId}"]:checked`);
        if (!answer) {
            return false;
        }
    }

    return true;
};

const sendQuestions = (isComplete, hasFinished) => {
    const questions = [];
    allQuestions.forEach(question => {
        const answer = document.querySelector(`input[name="answer-${!formProps.isNew ? question.id : question.perguntaId}"]:checked`);
        questions.push(
            {
                perguntaId: !formProps.isNew ? question.id : question.perguntaId,
                respostaUsuario: answer?.value ? answer.value : null,
                perguntaEixo: !formProps.isNew ? question.eixo : question.perguntaEixo,
            }
        );
    });
    const fullURL = `${URL}/auth/processarRespostas?empresa_id=${formProps.company.id}&is_complete=${isComplete}`;
    const body = questions
    fetch(fullURL, {
        method: 'POST',
        headers,
        body: JSON.stringify(body)
    }).then(response => {
        if (!response.ok) {
            throw new Error('Erro ao enviar dados');
        }
        return response.json();
    }).then(data => {
        if (isComplete) {
            toastAlert('Respostas enviadas com sucesso', 'success');
            getMainFrameContent('result-avaliacao', data);
        } else if (hasFinished) {
            toastAlert('Respostas salvas com sucesso', 'success');
            getMainFrameContent('ranking');
        }
    }).catch(() => {
        toastAlert('Erro ao enviar dados', 'error');
    });
}

const onOpenAvaliacao = (props) => {
    formProps = props;
    const form = document.querySelector('.form-avaliacao')
    form.addEventListener('submit', (event) => {
        if (event.explicitOriginalTarget !== document.getElementById("btn-submit")) {
            event.preventDefault();
        }
    });
    const governamental = [];
    const ambiental = [];
    const social = [];
    const checkListId = 2;

    fetch(`${URL}/auth/questionario/${checkListId}?empresaId=${props.company.id}`, options)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao recuperar dados');
            }
            return response.json();
        })
        .then(data => {
            if (!props.isNew) {
                data.formularioRequests.forEach(item => {
                    if (item.eixo === 'Governamental') {
                        if (governamental.length < questionNumbers) {
                            governamental.push(item);
                        }
                    } else if (item.eixo === 'Ambiental') {
                        if (ambiental.length < questionNumbers) {
                            ambiental.push(item);
                        }    
                    } else if (item.eixo === 'Social') {
                        if (social.length < questionNumbers) {
                            social.push(item);
                        }
                    }
                });
            } else {
                data.formularioRequests.forEach(item => {
                    delete item.numeroPergunta;
                    if (item.perguntaEixo === 'Governamental') {
                        governamental.push(item);
                    } else if (item.perguntaEixo === 'Ambiental') {
                        ambiental.push(item);
                    } else if (item.perguntaEixo === 'Social') {
                        social.push(item);
                    }
                });
            }

            allQuestions = [...governamental, ...ambiental, ...social];

            renderQuestions(governamental, ambiental.length === 0 && social.length === 0 ? true : false)
            renderQuestions(ambiental, social.length === 0 ? true : false)
            renderQuestions(social, true)
        })
        .catch(err => {
            console.error(err);
        });
};