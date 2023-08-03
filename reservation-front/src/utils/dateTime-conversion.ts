import { format } from 'date-fns';

export const formatDateToString = (date: Date) => {
    return format(date, 'yyyy-MM-dd\'T\'HH:mm');
}

