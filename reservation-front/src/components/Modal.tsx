import React from 'react';

interface ModalProps {
    id: string;
    title: string;
    children: React.ReactNode;
}

const Modal: React.FC<ModalProps> = ({ id, title, children }) => {
    return (
        <div className="modal fade" id={id} tabIndex={-1}>
            <div className="modal-dialog modal-xl">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{title}</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">{children}</div> {}
                </div>
            </div>
        </div>
    );
};

export default Modal;
