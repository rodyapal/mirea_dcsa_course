import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

interface CardProps {
    title: String
    content: String
    buttonText: String
}

export default function BasicCard(props: CardProps) {
    return (
        <Card sx={{ minWidth: 275 }}>
            <CardContent>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                    {props.title}
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    {props.content}
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small">{props.buttonText}</Button>
            </CardActions>
        </Card>
    );
}