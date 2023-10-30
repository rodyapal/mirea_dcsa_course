import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

export default function BasicCard() {
    return (
        <Card sx={{ minWidth: 275 }}>
            <CardContent>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                    Card title
                </Typography>
                <Typography variant="h5" component="div">
                    Card content one
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    Card content two
                </Typography>
                <Typography variant="body2">
                    Card content three
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small">Some button</Button>
            </CardActions>
        </Card>
    );
}