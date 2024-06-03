import { useRouteError } from "react-router-dom";

export default function ErrorElement() {
    const error: any = useRouteError();
    console.error(error);

    return (
        <div className="w-full h-screen flex flex-col items-center justify-center gap-6">
            <h1 className="text-4xl font-semibold">Oops!</h1>
            <p>Sorry, an unexpected error has occurred.</p>
            <p>
                <i className="text-gray-400">{error.statusText || error.message}</i>
            </p>
        </div>
    )
}