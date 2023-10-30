import Header from "./Header";
import BasicCard from "./Card"
import React from "react";

interface PageProps {
    title: String,
    cardTitle: String,
    cardButtonText: String
}

export default function Page(props: PageProps) {
    return (
        <div>
            <Header title={props.title}/>
            <div>
                <BasicCard
                    title={props.cardTitle}
                    content={(Math.random() + 1).toString(36).substring(7)}
                    buttonText={props.cardButtonText}/>
            </div>
        </div>
    )
}